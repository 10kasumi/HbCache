package com.cjl.message.stringMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class MGetStringMessage extends Message {
    private String[] keys;

    public MGetStringMessage() {
    }

    public MGetStringMessage(String[] keys) {
        this.keys = keys;
    }

    @Override
    public int getMessageType() {
        return MGetStringMessage;
    }
}
