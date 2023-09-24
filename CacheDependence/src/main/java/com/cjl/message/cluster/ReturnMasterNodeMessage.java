package com.cjl.message.cluster;

import com.cjl.cluster.NodeInfo;
import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class ReturnMasterNodeMessage extends Message {
    private NodeInfo nodeInfo;

    private NodeInfo masterInfo;

    public ReturnMasterNodeMessage(){

    }

    public ReturnMasterNodeMessage(NodeInfo nodeInfo, NodeInfo masterInfo){
        this.nodeInfo = nodeInfo;
        this.masterInfo = masterInfo;
    }

    @Override
    public int getMessageType() {
        return ReturnMasterNodeMessage;
    }
}
