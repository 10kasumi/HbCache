package com.cjl.message.stringMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class MSetStringMessage extends Message {
    private String[] keys;
    private String[] values;

    public MSetStringMessage() {
    }

    public MSetStringMessage(String[] keys, String[] values) {
        this.keys = keys;
        this.values = values;
    }

    @Override
    public int getMessageType() {
        return MSetStringMessage;
    }
}
