package com.cjl.message.cluster;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GetMasterNodeMessage extends Message {
    @Override
    public int getMessageType() {
        return GetMasterNodeMessage;
    }
}
