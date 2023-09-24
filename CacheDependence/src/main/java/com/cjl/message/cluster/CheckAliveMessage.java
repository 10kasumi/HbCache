package com.cjl.message.cluster;

import com.cjl.cluster.NodeInfo;
import com.cjl.message.Message;
import lombok.Data;

@Data
public class CheckAliveMessage extends Message {
    private NodeInfo nodeInfo;

    public CheckAliveMessage(NodeInfo nodeInfo){
        this.nodeInfo = nodeInfo;
    }

    public CheckAliveMessage(){

    }

    @Override
    public int getMessageType() {
        return CheckAliveMessage;
    }
}
