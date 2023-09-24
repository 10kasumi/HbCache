package com.cjl.handler.cluster;

import com.cjl.HbServer;
import com.cjl.cluster.NodeInfo;
import com.cjl.cluster.manager.SlaveManager;
import com.cjl.message.cluster.GetMasterNodeMessage;
import com.cjl.message.cluster.ReturnMasterNodeMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GetMasterNodeMessageHandler extends SimpleChannelInboundHandler<GetMasterNodeMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GetMasterNodeMessage getMasterNodeMessage) throws Exception {
        NodeInfo curNodeInfo = SlaveManager.curNodeInfo;
        NodeInfo masterInfo = HbServer.ClusterManager.getSlaveManager().getCurCacheServer().getServerNode().getNodeInfo();
        ReturnMasterNodeMessage message = new ReturnMasterNodeMessage(curNodeInfo, masterInfo);
        ctx.channel().writeAndFlush(message);
    }
}
