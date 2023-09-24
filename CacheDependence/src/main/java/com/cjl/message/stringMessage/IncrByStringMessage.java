package com.cjl.message.stringMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class IncrByStringMessage extends Message {
    private String key;

    private Integer step;

    public IncrByStringMessage() {
    }

    public IncrByStringMessage(String key, Integer step) {
        this.key = key;
        this.step = step;
    }

    @Override
    public int getMessageType() {
        return IncrByStringMessage;
    }
}
