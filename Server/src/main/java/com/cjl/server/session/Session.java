package com.cjl.server.session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Session{

    private static volatile Map<String, Channel> usernameChannelMap = new ConcurrentHashMap<>();
    private static volatile Map<Channel, String> channelUsernameMap = new ConcurrentHashMap<>();

    public static void bind(Channel channel, String username) {
        usernameChannelMap.put(username, channel);
        channelUsernameMap.put(channel, username);
    }


    public static void unbind(Channel channel) {
        String username = channelUsernameMap.remove(channel);
        if(username != null){
            usernameChannelMap.remove(username);
        }
    }

    public static Channel getChannel(String username) {
        return usernameChannelMap.get(username);
    }

    @Override
    public String toString() {
        return usernameChannelMap.toString();
    }
}
