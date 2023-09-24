package com.cjl.handler.nettyHandler.list;

import com.cjl.HbServer;
import com.cjl.cluster.NodeInfo;
import com.cjl.cluster.ServerNode;
import com.cjl.handler.HandlerManager;
import com.cjl.message.ResponseMessage;
import com.cjl.message.cluster.RedirectMessage;
import com.cjl.message.listMessage.LPopMessage;
import com.cjl.utils.CrcUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@ChannelHandler.Sharable
public class LPopMessageHandler extends SimpleChannelInboundHandler<LPopMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LPopMessage msg) throws Exception {
        if (!HbServer.enableCluster) {
            ResponseMessage responseMessage = HandlerManager.process(msg);
            ctx.writeAndFlush(responseMessage);
        } else {
            int slot = CrcUtils.CRC16(msg.getName().toCharArray());
            if (slot >= HbServer.ClusterManager.getSlaveManager().getStart()
                    && slot < HbServer.ClusterManager.getSlaveManager().getEnd()) {
                ResponseMessage responseMessage = HandlerManager.process(msg);
                ctx.writeAndFlush(responseMessage);
                ConcurrentHashMap<String, Channel> channelMap =
                        HbServer.ClusterManager.getSlaveManager().getChannelMap();
                List<NodeInfo> slaveNodes =
                        HbServer.ClusterManager.getSlaveManager().getCurCacheServer().getServerNode().getSlaveNodes();
                for (NodeInfo slaveNode : slaveNodes) {
                    Channel channel = channelMap.get(slaveNode.getHost() + ":" + slaveNode.getPort());
                    channel.writeAndFlush(msg);
                }
            } else {
                for (ServerNode masterNode : HbServer.ClusterManager.getMasterNodes()) {
                    if (masterNode.getStart() >= slot && slot < masterNode.getStart()) {
                        ctx.channel().writeAndFlush(new RedirectMessage(masterNode.getNodeInfo().getHost(),
                                masterNode.getNodeInfo().getPort(), msg));
                    }
                }
            }
        }
    }
}
