package com.cjl.handler.nettyHandler.set;

import com.cjl.handler.HandlerManager;
import com.cjl.message.ResponseMessage;
import com.cjl.message.setMessage.SInterMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class SInterMessageHandler extends SimpleChannelInboundHandler<SInterMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SInterMessage msg) throws Exception {
        ResponseMessage responseMessage = HandlerManager.process(msg);
        ctx.writeAndFlush(responseMessage);
    }
}
