package com.cjl.handler.nettyHandler.normal;

import com.cjl.server.session.Session;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ChannelHandler.Sharable
public class QuitHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LogManager.getLogger(QuitHandler.class);
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Session.unbind(ctx.channel());
        LOGGER.debug("{} 断开连接", ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Session.unbind(ctx.channel());
        LOGGER.debug("{} 异常断开 异常是 {}", ctx.channel(), cause.getCause());
    }
}
