package com.cjl.handler;

import com.cjl.HbServer;
import com.cjl.cluster.manager.SlaveManager;
import com.cjl.message.cluster.GetMasterNodeMessage;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerConnectHandler extends ChannelDuplexHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;
        if(event.state() == IdleState.WRITER_IDLE){
            ctx.writeAndFlush(SlaveManager.curNodeInfo);
            ctx.writeAndFlush(new GetMasterNodeMessage());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        HbServer.ClusterManager.getSlaveManager().nodeInactive(SlaveManager.curNodeInfo);
    }
}
