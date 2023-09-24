package com.cjl.message.hashMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class HGetMessage extends Message {
    private String name;
    private String key;

    @Override
    public int getMessageType() {
        return HGetMessage;
    }
}
