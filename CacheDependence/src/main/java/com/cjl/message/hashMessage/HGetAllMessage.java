package com.cjl.message.hashMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class HGetAllMessage extends Message {
    private String name;

    public HGetAllMessage() {
    }

    public HGetAllMessage(String name) {
        this.name = name;
    }

    @Override
    public int getMessageType() {
        return HGetAllMessage;
    }
}
