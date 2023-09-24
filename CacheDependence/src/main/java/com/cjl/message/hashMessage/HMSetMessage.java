package com.cjl.message.hashMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class HMSetMessage extends Message {
    private String name;
    private String[] keys;
    private String[] values;

    @Override
    public int getMessageType() {
        return HMSetMessage;
    }
}
