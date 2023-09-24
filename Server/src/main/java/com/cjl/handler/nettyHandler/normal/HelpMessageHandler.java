package com.cjl.handler.nettyHandler.normal;

import com.cjl.handler.HandlerManager;
import com.cjl.message.HelpMessage;
import com.cjl.message.ResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class HelpMessageHandler extends SimpleChannelInboundHandler<HelpMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HelpMessage msg) throws Exception {
        ResponseMessage responseMessage = HandlerManager.process(msg);
        ctx.writeAndFlush(responseMessage);
    }
}
