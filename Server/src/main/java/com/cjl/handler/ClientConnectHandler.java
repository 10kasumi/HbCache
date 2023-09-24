package com.cjl.handler;

import com.cjl.HbServer;
import com.cjl.cluster.manager.SlaveManager;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ClientConnectHandler extends ChannelDuplexHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;
        if(event.state() == IdleState.WRITER_IDLE){
            ctx.writeAndFlush(SlaveManager.curNodeInfo);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        HbServer.ClusterManager.getSlaveManager().getChannelMap().clear();
        HbServer.ClusterManager.getSlaveManager().getActiveNodes().clear();
    }
}
