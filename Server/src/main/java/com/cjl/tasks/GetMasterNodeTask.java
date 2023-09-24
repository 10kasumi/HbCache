package com.cjl.tasks;

import com.cjl.message.cluster.GetMasterNodeMessage;
import io.netty.channel.Channel;

import java.util.Collection;

public class GetMasterNodeTask implements Runnable{

    private Collection<Channel> channels;


    @Override
    public void run() {
        for (Channel channel : channels) {
            channel.writeAndFlush(new GetMasterNodeMessage());
        }
    }

    public GetMasterNodeTask(Collection<Channel> channels){
        this.channels = channels;
    }
}
