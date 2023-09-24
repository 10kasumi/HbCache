package com.cjl.message.stringMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class SetStringMessage extends Message {
    private String key;
    private String value;

    public SetStringMessage() {
    }

    public SetStringMessage(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int getMessageType() {
        return SetStringMessage;
    }
}
