package com.cjl.handler.nettyHandler.set;

import com.cjl.HbServer;
import com.cjl.cluster.ServerNode;
import com.cjl.handler.HandlerManager;
import com.cjl.message.ResponseMessage;
import com.cjl.message.cluster.RedirectMessage;
import com.cjl.message.setMessage.SMembersMessage;
import com.cjl.utils.CrcUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class SMemberMessageHandler extends SimpleChannelInboundHandler<SMembersMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SMembersMessage msg) throws Exception {
        if(!HbServer.enableCluster){
            ResponseMessage responseMessage = HandlerManager.process(msg);
            ctx.writeAndFlush(responseMessage);
        } else {
            if (!HbServer.enableCluster) {
                ResponseMessage responseMessage = HandlerManager.process(msg);
                ctx.writeAndFlush(responseMessage);
            } else {
                int slot = CrcUtils.CRC16(msg.getName().toCharArray());
                if (slot >= HbServer.ClusterManager.getSlaveManager().getStart()
                        && slot < HbServer.ClusterManager.getSlaveManager().getEnd()) {
                    ResponseMessage responseMessage = HandlerManager.process(msg);
                    ctx.writeAndFlush(responseMessage);
                } else {
                    for (ServerNode masterNode : HbServer.ClusterManager.getMasterNodes()) {
                        if (masterNode.getStart() >= slot && masterNode.getEnd() < slot) {
                            ctx.channel().writeAndFlush(new RedirectMessage(masterNode.getNodeInfo().getHost(), masterNode.getNodeInfo().getPort(), msg));
                        }
                    }
                }
            }
        }
    }
}
