package com.cjl.server;


import com.cjl.cluster.ServerNode;
import com.cjl.handler.cluster.*;
import com.cjl.handler.nettyHandler.hash.*;
import com.cjl.handler.nettyHandler.list.*;
import com.cjl.handler.nettyHandler.normal.*;
import com.cjl.handler.nettyHandler.set.*;
import com.cjl.handler.nettyHandler.string.*;
import com.cjl.protocol.MessageCodecSharable;
import com.cjl.protocol.MessageProtocolFrameDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class CacheServer {
    private static final Logger LOGGER = LogManager.getLogger(CacheServer.class);

    private ServerNode serverNode;

    public ServerNode getServerNode() {
        return serverNode;
    }

    public void setServerNode(ServerNode serverNode) {
        this.serverNode = serverNode;
    }

    public void initServer(){
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.INFO);
        MessageCodecSharable messageCodec = new MessageCodecSharable();

        DeleteMessageHandler deleteMessageHandler = new DeleteMessageHandler();
        ExistMessageHandler existMessageHandler = new ExistMessageHandler();
        ExpireMessageHandler expireMessageHandler = new ExpireMessageHandler();
        HelpMessageHandler helpMessageHandler = new HelpMessageHandler();
        QuitHandler quitHandler = new QuitHandler();
        TtlMessageHandler ttlMessageHandler = new TtlMessageHandler();
        LoginRequestMessageHandler loginRequestMessageHandler = new LoginRequestMessageHandler();
        ResponseMessageHandler responseMessageHandler = new ResponseMessageHandler();

        GetStringMessageHandler getStringMessageHandler = new GetStringMessageHandler();
        IncrByStringMessageHandler incrByStringMessageHandler = new IncrByStringMessageHandler();
        IncrStringMessageHandler incrStringMessageHandler = new IncrStringMessageHandler();
        MGetStringMessageHandler mGetStringMessageHandler = new MGetStringMessageHandler();
        MSetStringMessageHandler mSetStringMessageHandler = new MSetStringMessageHandler();
        SetExStringMessageHandler setExStringMessageHandler = new SetExStringMessageHandler();
        SetNxStringMessageHandler setNxStringMessageHandler = new SetNxStringMessageHandler();
        SetStringMessageHandler setStringMessageHandler = new SetStringMessageHandler();

        SAddMessageHandler sAddMessageHandler = new SAddMessageHandler();
        SCardMessageHandler sCardMessageHandler = new SCardMessageHandler();
        SIsMemberMessageHandler sIsMemberMessageHandler = new SIsMemberMessageHandler();
        SMemberMessageHandler sMemberMessageHandler = new SMemberMessageHandler();
        SRemMessageHandler sRemMessageHandler = new SRemMessageHandler();
        SInterMessageHandler sInterMessageHandler = new SInterMessageHandler();
        SDiffMessageHandler sDiffMessageHandler = new SDiffMessageHandler();
        SUnionMessageHandler sUnionMessageHandler = new SUnionMessageHandler();

        BLpopMessageHandler bLpopMessageHandler = new BLpopMessageHandler();
        BRpopMessageHandler bRpopMessageHandler = new BRpopMessageHandler();
        LPopMessageHandler lPopMessageHandler = new LPopMessageHandler();
        LPushMessageHandler lPushMessageHandler = new LPushMessageHandler();
        RPopMessageHandler rPopMessageHandler = new RPopMessageHandler();
        RPushMessageHandler rPushMessageHandler = new RPushMessageHandler();
        LRangeMessageHandler lRangeMessageHandler = new LRangeMessageHandler();

        HGetAllMessageHandler hGetAllMessageHandler = new HGetAllMessageHandler();
        HGetMessageHandler hGetMessageHandler = new HGetMessageHandler();
        HIncrByMessageHandler hIncrByMessageHandler = new HIncrByMessageHandler();
        HKeysMessageHandler hKeysMessageHandler = new HKeysMessageHandler();
        HMGetMessageHandler hmGetMessageHandler = new HMGetMessageHandler();
        HMSetMessageHandler hmSetMessageHandler = new HMSetMessageHandler();
        HSetMessageHandler hSetMessageHandler = new HSetMessageHandler();
        HSetNxMessageHandler hSetNxMessageHandler = new HSetNxMessageHandler();
        HValuesMessageHandler hValuesMessageHandler = new HValuesMessageHandler();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new IdleStateHandler(60, 60, 120, TimeUnit.SECONDS));
                    ch.pipeline().addLast(new ChannelDuplexHandler() {
                        // 用来触发特殊事件
                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
                            IdleStateEvent event = (IdleStateEvent) evt;
                            // 触发了读空闲事件
                            if (event.state() == IdleState.READER_IDLE) {
                                LOGGER.info("已经有2分钟没有消息了");
                                ctx.channel().close();
                            }
                        }
                    });
                    ch.pipeline().addLast(new MessageProtocolFrameDecoder());
//                    ch.pipeline().addLast(LOGGING_HANDLER);
                    ch.pipeline().addLast(messageCodec);
                    ch.pipeline().addLast(loginRequestMessageHandler);
                    ch.pipeline().addLast(deleteMessageHandler);
                    ch.pipeline().addLast(existMessageHandler);
                    ch.pipeline().addLast(expireMessageHandler);
                    ch.pipeline().addLast(helpMessageHandler);
                    ch.pipeline().addLast(quitHandler);
                    ch.pipeline().addLast(ttlMessageHandler);
                    ch.pipeline().addLast(responseMessageHandler);

                    ch.pipeline().addLast(getStringMessageHandler);
                    ch.pipeline().addLast(incrByStringMessageHandler);
                    ch.pipeline().addLast(incrStringMessageHandler);
                    ch.pipeline().addLast(mGetStringMessageHandler);
                    ch.pipeline().addLast(mSetStringMessageHandler);
                    ch.pipeline().addLast(setNxStringMessageHandler);
                    ch.pipeline().addLast(setExStringMessageHandler);
                    ch.pipeline().addLast(setStringMessageHandler);

                    ch.pipeline().addLast(sAddMessageHandler);
                    ch.pipeline().addLast(sCardMessageHandler);
                    ch.pipeline().addLast(sIsMemberMessageHandler);
                    ch.pipeline().addLast(sMemberMessageHandler);
                    ch.pipeline().addLast(sRemMessageHandler);
                    ch.pipeline().addLast(sInterMessageHandler);
                    ch.pipeline().addLast(sDiffMessageHandler);
                    ch.pipeline().addLast(sUnionMessageHandler);

                    ch.pipeline().addLast(bLpopMessageHandler);
                    ch.pipeline().addLast(bRpopMessageHandler);
                    ch.pipeline().addLast(lPopMessageHandler);
                    ch.pipeline().addLast(lPushMessageHandler);
                    ch.pipeline().addLast(rPopMessageHandler);
                    ch.pipeline().addLast(rPushMessageHandler);
                    ch.pipeline().addLast(lRangeMessageHandler);

                    ch.pipeline().addLast(hGetAllMessageHandler);
                    ch.pipeline().addLast(hGetMessageHandler);
                    ch.pipeline().addLast(hIncrByMessageHandler);
                    ch.pipeline().addLast(hKeysMessageHandler);
                    ch.pipeline().addLast(hmGetMessageHandler);
                    ch.pipeline().addLast(hmSetMessageHandler);
                    ch.pipeline().addLast(hSetMessageHandler);
                    ch.pipeline().addLast(hSetNxMessageHandler);
                    ch.pipeline().addLast(hValuesMessageHandler);

                    ch.pipeline().addLast(new RedirectMessageHandler());
                    ch.pipeline().addLast(new CheckAliveMessageHandler());
                    ch.pipeline().addLast(new VoteRequestMessageHandler());
                    ch.pipeline().addLast(new VoteResponseMessageHandler());
                    ch.pipeline().addLast(new GetMasterNodeMessageHandler());
                    ch.pipeline().addLast(new SetMasterMessageHandler());
                    ch.pipeline().addLast(new ReturnMasterNodeMessageHandler());
                }
            });
            Channel channel = serverBootstrap.bind(serverNode.getNodeInfo().getPort()).sync().channel();
            ChannelFuture channelFuture = channel.closeFuture();
            channelFuture.sync();
        } catch (InterruptedException e) {
            LOGGER.error("server error");
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
