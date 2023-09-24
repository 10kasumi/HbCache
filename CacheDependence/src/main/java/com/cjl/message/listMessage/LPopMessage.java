package com.cjl.message.listMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;


@Data
@ToString(callSuper = true)
public class LPopMessage extends Message {
    private String name;

    public LPopMessage() {
    }

    public LPopMessage(String name) {
        this.name = name;
    }

    @Override
    public int getMessageType() {
        return LPopMessage;
    }
}
