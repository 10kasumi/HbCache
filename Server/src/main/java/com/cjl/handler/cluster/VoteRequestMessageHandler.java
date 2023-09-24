package com.cjl.handler.cluster;

import com.cjl.HbServer;
import com.cjl.message.cluster.VoteRequestMessage;
import com.cjl.message.cluster.VoteResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class VoteRequestMessageHandler extends SimpleChannelInboundHandler<VoteRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, VoteRequestMessage voteRequestMessage) throws Exception {
        int curEpoch = HbServer.ClusterManager.getSlaveManager().getCurCacheServer().getServerNode().getEpoch();
        if(voteRequestMessage.getEpoch() > curEpoch){
            ctx.channel().writeAndFlush(new VoteResponseMessage(200));
        } else {
            ctx.channel().writeAndFlush(new VoteResponseMessage(400));
        }
    }
}
