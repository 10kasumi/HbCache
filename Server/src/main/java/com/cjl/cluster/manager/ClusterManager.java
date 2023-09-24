package com.cjl.cluster.manager;

import com.cjl.cluster.ServerNode;
import com.cjl.config.ThreadConfig;
import com.cjl.tasks.CheckAliveTask;
import com.cjl.tasks.GetMasterNodeTask;
import com.cjl.utils.ServerPropertiesUtils;
import io.netty.channel.Channel;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Data
public class ClusterManager {

    private volatile Set<String> masterUrlSet;

    private SlaveManager slaveManager;

    private ConcurrentHashMap<String, Channel> clusterChannelMap;

    private int nodeSize;

    private List<ServerNode> masterNodes;

    private final int slots = 16384;

    public ClusterManager() {
        slaveManager = new SlaveManager();
        masterUrlSet = new HashSet<>();
        masterNodes = new ArrayList<>();
        String[] nodes = ServerPropertiesUtils.getNodes().split(",");
        clusterChannelMap = new ConcurrentHashMap<>();
        slaveManager.informationSynchronize(masterUrlSet, clusterChannelMap);

        CheckAliveTask checkAliveTask = new CheckAliveTask(clusterChannelMap.values(), SlaveManager.curNodeInfo);
        GetMasterNodeTask getMasterNodeTask = new GetMasterNodeTask(clusterChannelMap.values());

        ScheduledExecutorService clusterThreadPool = ThreadConfig.clusterThreadPool;
        clusterThreadPool.scheduleAtFixedRate(checkAliveTask,1, 30, TimeUnit.SECONDS);
        clusterThreadPool.scheduleAtFixedRate(getMasterNodeTask, 3, 10, TimeUnit.SECONDS);

        int gap = slots / (masterUrlSet.size() + 1);

        int idx = 1;
        for (ServerNode masterNode : masterNodes) {
            masterNode.setStart((idx - 1) * gap);
            masterNode.setEnd(idx * gap);
            idx++;
            if((idx + 1) * gap >= slots){
                masterNode.setEnd(slots - 1);
            }
        }
    }

}
