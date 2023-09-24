package com.cjl.handler.nettyHandler.normal;

import com.cjl.HbServer;
import com.cjl.cluster.ServerNode;
import com.cjl.handler.HandlerManager;
import com.cjl.message.ResponseMessage;
import com.cjl.message.TtlMessage;
import com.cjl.message.cluster.RedirectMessage;
import com.cjl.utils.CrcUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class TtlMessageHandler extends SimpleChannelInboundHandler<TtlMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TtlMessage msg) throws Exception {
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
                    if (masterNode.getStart() >= slot && slot < masterNode.getEnd()) {
                        ctx.channel().writeAndFlush(new RedirectMessage(masterNode.getNodeInfo().getHost(), masterNode.getNodeInfo().getPort(), msg));
                    }
                }
            }
        }
    }
}
