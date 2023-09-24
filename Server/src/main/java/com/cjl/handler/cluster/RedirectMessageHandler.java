package com.cjl.handler.cluster;

import com.cjl.HbServer;
import com.cjl.message.cluster.RedirectMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RedirectMessageHandler extends SimpleChannelInboundHandler<RedirectMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RedirectMessage redirectMessage) throws Exception {
        Channel channel = HbServer.ClusterManager.getClusterChannelMap().get(redirectMessage.getHost() + ":" + redirectMessage.getPort());
        channel.writeAndFlush(redirectMessage);
    }
}
