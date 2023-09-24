package com.cjl.handler.cluster;

import com.cjl.HbServer;
import com.cjl.cluster.NodeInfo;
import com.cjl.message.cluster.CheckAliveMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CheckAliveMessageHandler extends SimpleChannelInboundHandler<CheckAliveMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CheckAliveMessage checkAliveMessage) throws Exception {
        HbServer.ClusterManager.getSlaveManager().nodeActive(checkAliveMessage.getNodeInfo());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        HbServer.ClusterManager.getSlaveManager().getCurCacheServer().getServerNode().getSlaveNodes().clear();
        HbServer.ClusterManager.getSlaveManager().getCurCacheServer().getServerNode().setMaster(false);

        Set<NodeInfo> activeNodes = HbServer.ClusterManager.getSlaveManager().getActiveNodes();
        ConcurrentHashMap<String, Channel> channelMap = HbServer.ClusterManager.getSlaveManager().getChannelMap();
        for (Map.Entry<String, Channel> entry : channelMap.entrySet()) {
            if(entry.getValue() == ctx.channel()){
                String[] s = entry.getKey().split(":");
                for (NodeInfo activeNode : activeNodes) {
                    if(activeNode.getHost().equals(s[0]) && activeNode.getPort() == Integer.parseInt(s[1])){
                        HbServer.ClusterManager.getSlaveManager().nodeInactive(activeNode);
                    }
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        channelInactive(ctx);
        ctx.close();
    }
}
