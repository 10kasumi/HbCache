package com.cjl.message.hashMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class HMGetMessage extends Message {
    private String name;
    private String[] keys;
    @Override
    public int getMessageType() {
        return HMGetMessage;
    }
}
