package com.cjl.handler.nettyHandler.set;

import com.cjl.handler.HandlerManager;
import com.cjl.message.ResponseMessage;
import com.cjl.message.setMessage.SUnionMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class SUnionMessageHandler extends SimpleChannelInboundHandler<SUnionMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SUnionMessage msg) throws Exception {
        ResponseMessage responseMessage = HandlerManager.process(msg);
        ctx.writeAndFlush(responseMessage);
    }
}
