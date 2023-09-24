package com.cjl.handler.cluster;

import com.cjl.HbServer;
import com.cjl.message.cluster.VoteResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class VoteResponseMessageHandler extends SimpleChannelInboundHandler<VoteResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, VoteResponseMessage voteResponseMessage) throws Exception {
        if(voteResponseMessage.getCode() == 200){
            HbServer.ClusterManager.getSlaveManager().getAtomicInteger().incrementAndGet();
        }
    }
}
