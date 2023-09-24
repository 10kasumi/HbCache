package com.cjl.message.stringMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class IncrStringMessage extends Message {
    private String key;

    public IncrStringMessage() {
    }

    public IncrStringMessage(String key) {
        this.key = key;
    }

    @Override
    public int getMessageType() {
        return IncrStringMessage;
    }
}
