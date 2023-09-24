package com.cjl.message.listMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class RPopMessage extends Message {
    private String name;

    public RPopMessage() {
    }

    public RPopMessage(String name) {
        this.name = name;
    }

    @Override
    public int getMessageType() {
        return RPopMessage;
    }
}
