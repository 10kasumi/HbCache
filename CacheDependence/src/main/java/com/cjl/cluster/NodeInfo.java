package com.cjl.cluster;

import java.io.Serializable;

public class NodeInfo implements Serializable {

    private int port;
    private String host;

    public String getHost() {
        return host;
    }


    public int getPort(){
        return port;
    }

    public NodeInfo(int port, String host) {
        this.port = port;
        this.host = host;
    }


    @Override
    public boolean equals(Object obj) {
        NodeInfo nodeInfo = (NodeInfo) obj;
        return this.host.equals(nodeInfo.getHost()) && this.port == nodeInfo.getPort();
    }
}
