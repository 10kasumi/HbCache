package com.cjl.message.cluster;

import com.cjl.cluster.NodeInfo;
import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class VoteRequestMessage extends Message {
    private NodeInfo nodeInfo;

    private int epoch;

    public VoteRequestMessage(NodeInfo nodeInfo){
        this.nodeInfo = nodeInfo;
    }

    @Override
    public int getMessageType() {
        return VoteRequestMessage;
    }
}
