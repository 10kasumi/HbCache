package com.cjl.message.cluster;

import com.cjl.cluster.NodeInfo;
import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class SetMasterNodeMessage extends Message {
    private NodeInfo nodeInfo;

    public SetMasterNodeMessage(NodeInfo nodeInfo){
        this.nodeInfo = nodeInfo;
    }


    @Override
    public int getMessageType() {
        return 0;
    }
}
