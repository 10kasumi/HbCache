package com.cjl.handler.nettyHandler.string;

import com.cjl.HbServer;
import com.cjl.cluster.NodeInfo;
import com.cjl.cluster.ServerNode;
import com.cjl.handler.HandlerManager;
import com.cjl.message.ResponseMessage;
import com.cjl.message.cluster.RedirectMessage;
import com.cjl.message.stringMessage.GetStringMessage;
import com.cjl.message.stringMessage.MGetStringMessage;
import com.cjl.utils.CrcUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@ChannelHandler.Sharable
public class MGetStringMessageHandler extends SimpleChannelInboundHandler<MGetStringMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MGetStringMessage msg) throws Exception {
        if (!HbServer.enableCluster) {
            ResponseMessage responseMessage = HandlerManager.process(msg);
            ctx.writeAndFlush(responseMessage);
        } else {
            String[] keys = msg.getKeys();
            for (int i = 0; i < keys.length; ++i) {
                int slot = CrcUtils.CRC16(keys[i].toCharArray());
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
                        channel.writeAndFlush(new GetStringMessage(keys[i]));
                    }
                } else {
                    for (ServerNode masterNode : HbServer.ClusterManager.getMasterNodes()) {
                        if (masterNode.getStart() >= slot && slot < masterNode.getStart()) {
                            ctx.channel().writeAndFlush(new RedirectMessage(masterNode.getNodeInfo().getHost(),
                                    masterNode.getNodeInfo().getPort(), new GetStringMessage(keys[i])));
                        }
                    }
                }
            }
        }
    }
}
