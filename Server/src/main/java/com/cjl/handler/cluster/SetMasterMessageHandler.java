package com.cjl.handler.cluster;

import com.cjl.HbServer;
import com.cjl.cluster.NodeInfo;
import com.cjl.cluster.ServerNode;
import com.cjl.cluster.manager.SlaveManager;
import com.cjl.message.cluster.SetMasterNodeMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

public class SetMasterMessageHandler extends SimpleChannelInboundHandler<SetMasterNodeMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SetMasterNodeMessage setMasterNodeMessage) throws Exception {
        NodeInfo nodeInfo = setMasterNodeMessage.getNodeInfo();
        SlaveManager slaveManager = HbServer.ClusterManager.getSlaveManager();
        List<ServerNode> nodeList = slaveManager.getNodeList();
        int epoch = 0;
        for (ServerNode serverNode : nodeList) {
            if(serverNode.getNodeInfo().equals(nodeInfo)){
                for (ServerNode node : nodeList) {
                    serverNode.getSlaveNodes().add(node.getNodeInfo());
                }
                serverNode.setMaster(true);
                serverNode.setMasterUrl(nodeInfo.getHost() + ":" + nodeInfo.getPort());
                epoch = serverNode.getEpoch() + 1;
                serverNode.setEpoch(epoch);
            }
            serverNode.setEpoch(epoch);
            serverNode.setMasterUrl(nodeInfo.getHost() + ":" +nodeInfo.getPort());
            serverNode.setMaster(false);
            serverNode.getSlaveNodes().clear();
        }
    }
}
