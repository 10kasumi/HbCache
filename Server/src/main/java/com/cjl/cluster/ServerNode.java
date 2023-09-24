package com.cjl.cluster;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ServerNode {

    private NodeInfo nodeInfo;

    private boolean isMaster;

    private String masterUrl;

    private int epoch;

    private List<NodeInfo> slaveNodes;

    //[)区间
    private int start;

    private int end;

    public ServerNode(NodeInfo nodeInfo){
        this.nodeInfo = nodeInfo;
        this.epoch = 0;
        slaveNodes = new ArrayList<>();
        masterUrl = nodeInfo.getHost() + ":" + nodeInfo.getPort();
    }
}
