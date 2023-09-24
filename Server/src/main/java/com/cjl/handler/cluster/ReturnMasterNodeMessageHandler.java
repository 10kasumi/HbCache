package com.cjl.handler.cluster;

import com.cjl.HbServer;
import com.cjl.cluster.NodeInfo;
import com.cjl.message.cluster.ReturnMasterNodeMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Set;

public class ReturnMasterNodeMessageHandler extends SimpleChannelInboundHandler<ReturnMasterNodeMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ReturnMasterNodeMessage returnMasterNodeMessage) throws Exception {
        NodeInfo nodeInfo = returnMasterNodeMessage.getNodeInfo();
        NodeInfo masterInfo = returnMasterNodeMessage.getMasterInfo();
        Set<String> masterUrlSet = HbServer.ClusterManager.getMasterUrlSet();
        masterUrlSet.remove(nodeInfo.getHost() + ":" + nodeInfo.getPort());
        masterUrlSet.add(masterInfo.getHost() + ":" + masterInfo.getPort());
    }
}
