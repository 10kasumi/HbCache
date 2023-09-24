package com.cjl.cluster.manager;

import com.cjl.cluster.NodeInfo;
import com.cjl.cluster.ServerNode;
import com.cjl.handler.ClientConnectHandler;
import com.cjl.message.cluster.GetMasterNodeMessage;
import com.cjl.message.cluster.SetMasterNodeMessage;
import com.cjl.message.cluster.VoteRequestMessage;
import com.cjl.protocol.MessageCodecSharable;
import com.cjl.protocol.MessageProtocolFrameDecoder;
import com.cjl.server.CacheServer;
import com.cjl.utils.ServerPropertiesUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Data;
import lombok.Synchronized;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class SlaveManager {
    public static final NodeInfo curNodeInfo = new NodeInfo(ServerPropertiesUtils.getServerPort(), "http://127.0.0.1");

    private Bootstrap bootstrap;

    private volatile boolean isElect;

    private CacheServer curCacheServer;

    private List<ServerNode> nodeList;


    private volatile Set<NodeInfo> activeNodes;

    private ConcurrentHashMap<String, Channel> channelMap;

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public SlaveManager() {
        curCacheServer = new CacheServer();
        nodeList = new ArrayList<>();
        activeNodes = new HashSet<>();
        String[] urls = ServerPropertiesUtils.getNodes().split(",");
        for (String url : urls) {
            int lastIdx = url.lastIndexOf(":");
            String host = url.substring(0, lastIdx);
            int port = Integer.parseInt(url.substring(lastIdx + 1));
            nodeList.add(new ServerNode(new NodeInfo(port, host)));
        }
        channelMap = new ConcurrentHashMap<>();
        bootstrap = newClient(curNodeInfo.getHost(), curNodeInfo.getPort());
        for (ServerNode serverNode : nodeList) {
            NodeInfo nodeInfo = serverNode.getNodeInfo();
            if (nodeInfo.getHost() != curNodeInfo.getHost() && nodeInfo.getPort() != curNodeInfo.getPort()) {
                Channel channel = null;
                try {
                    channel = bootstrap.connect(nodeInfo.getHost(), nodeInfo.getPort()).sync().channel();
                    channelMap.put(nodeInfo.getHost() + ":" + nodeInfo.getPort(), channel);
                    channel.writeAndFlush(new NodeInfo(nodeInfo.getPort(), nodeInfo.getHost()));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        elect();
    }

    private Bootstrap newClient(String host, int port) {
        Bootstrap bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new IdleStateHandler(30, 30, 60, TimeUnit.SECONDS));
                        channel.pipeline().addLast(new MessageProtocolFrameDecoder());
                        channel.pipeline().addLast(new MessageCodecSharable());
                        channel.pipeline().addLast(new ClientConnectHandler());

                    }
                });
        return bootstrap;
    }

    public void elect() {
        if (isElect) {
            doElect();
        }
        int i = 1;
        while (i < 100) {
            if (isElect) {
                doElect();
            }
            i++;
        }

    }

    @Synchronized
    public void doElect() {
        for (Map.Entry<String, Channel> entry : channelMap.entrySet()) {
            entry.getValue().writeAndFlush(new VoteRequestMessage(curNodeInfo));
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(atomicInteger.intValue() >= activeNodes.size() / 2 + 1){
            for (Channel channel : channelMap.values()) {
                channel.writeAndFlush(new SetMasterNodeMessage(curNodeInfo));
            }
            curCacheServer.getServerNode().getSlaveNodes().clear();
            curCacheServer.getServerNode().setMaster(true);
            for (ServerNode serverNode : nodeList) {
                serverNode.setEpoch(serverNode.getEpoch() + 1);
                serverNode.setMasterUrl(curNodeInfo.getHost() + ":" + curNodeInfo.getPort());
                if(!serverNode.getNodeInfo().equals(curNodeInfo)){
                    serverNode.setMaster(false);
                    curCacheServer.getServerNode().getSlaveNodes().add(serverNode.getNodeInfo());

                }
            }
        }
    }

    @Synchronized
    public void nodeActive(NodeInfo nodeInfo) {
        activeNodes.add(nodeInfo);
    }

    @Synchronized
    public void nodeInactive(NodeInfo nodeInfo) {
        activeNodes.remove(nodeInfo);
        channelMap.remove(nodeInfo + ":" + nodeInfo.getPort());
    }


    public void informationSynchronize(Set<String> masterUrlSet, ConcurrentHashMap<String, Channel> clusterChannelMap){
        Bootstrap bootstrap = getBootstrap();
        for(String url : masterUrlSet){
            String[] s = url.split(":");
            try {
                Channel channel = bootstrap.connect(s[0], Integer.parseInt(s[1])).sync().channel();
                clusterChannelMap.put(url, channel);
                channel.writeAndFlush(new GetMasterNodeMessage());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getStart(){
        return curCacheServer.getServerNode().getStart();
    }

    public int getEnd(){
        return curCacheServer.getServerNode().getEnd();
    }
}
