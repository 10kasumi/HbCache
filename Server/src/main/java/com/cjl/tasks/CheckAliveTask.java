package com.cjl.tasks;

import com.cjl.cluster.NodeInfo;
import com.cjl.message.cluster.CheckAliveMessage;
import io.netty.channel.Channel;

import java.util.Collection;


public class CheckAliveTask implements Runnable{

    private Collection<Channel> channels;

    private NodeInfo nodeInfo;


    @Override
    public void run() {
        for (Channel channel : channels) {
            channel.writeAndFlush(new CheckAliveMessage());
        }
    }

    public CheckAliveTask(Collection<Channel> channels, NodeInfo nodeInfo) {
        this.channels = channels;
        this.nodeInfo = nodeInfo;
    }
}
