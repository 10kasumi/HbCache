package com.cjl.client;


import com.cjl.client.handler.CommandReceiveHandler;
import com.cjl.message.PingMessage;
import com.cjl.protocol.MessageCodecSharable;
import com.cjl.protocol.MessageProtocolFrameDecoder;
import com.cjl.utils.ClientPropertiesUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HbClient {
    private static String host = null;
    private static Integer port = 0;

    public static void main(String[] args) throws IOException {

        host = ClientPropertiesUtils.getServerHost();
        port = ClientPropertiesUtils.getServerPort();
        System.out.println(port);
        NioEventLoopGroup group = new NioEventLoopGroup();
        MessageCodecSharable messageCodec = new MessageCodecSharable();

        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(group);
            bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) {

                    ch.pipeline().addLast(new IdleStateHandler(0, 60 * 15, 0, TimeUnit.SECONDS));
                    ch.pipeline().addLast(new ChannelDuplexHandler() {
                        // 用来触发特殊事件
                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                            IdleStateEvent event = (IdleStateEvent) evt;
                            // 触发了写空闲事件
                            if (event.state() == IdleState.WRITER_IDLE) {
                                ctx.writeAndFlush(new PingMessage());
                            }
                        }
                    });
                    ch.pipeline().addLast(new MessageProtocolFrameDecoder());
//                    ch.pipeline().addLast(LOGGING_HANDLER);
                    ch.pipeline().addLast(messageCodec);
                    ch.pipeline().addLast(new CommandReceiveHandler());
                }
            });
            Channel channel = bootstrap.connect(host, port).sync().channel();
            ChannelFuture channelFuture = channel.closeFuture();
            channelFuture.sync();
        } catch (Exception e) {
            log.error("client error", e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
