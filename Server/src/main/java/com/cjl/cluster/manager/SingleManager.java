package com.cjl.cluster.manager;

import com.cjl.cluster.NodeInfo;
import com.cjl.cluster.ServerNode;
import com.cjl.config.ThreadConfig;
import com.cjl.server.CacheServer;
import com.cjl.tasks.RecoveryTask;
import com.cjl.utils.ServerPropertiesUtils;

import java.util.concurrent.ExecutorService;

public class SingleManager {

    private CacheServer cacheServer;

    public CacheServer getServer() {
        return cacheServer;
    }

    public SingleManager(){

        int port = ServerPropertiesUtils.getServerPort();
        System.out.println(port);
        NodeInfo nodeInfo = new NodeInfo(port, "http://127.0.0.1");
        ServerNode serverNode = new ServerNode(nodeInfo);
        serverNode.setStart(0);
        serverNode.setEnd(16384);

        ExecutorService recoveryThreadPool = ThreadConfig.recoveryThread;
        recoveryThreadPool.submit(new RecoveryTask());

        cacheServer = new CacheServer();
        cacheServer.setServerNode(serverNode);
        cacheServer.initServer();
    }
}
