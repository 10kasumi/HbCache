package com.cjl.message.hashMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;


@Data
@ToString(callSuper = true)
public class HKeysMessage extends Message {
    private String name;

    public HKeysMessage() {
    }

    public HKeysMessage(String name) {
        this.name = name;
    }

    @Override
    public int getMessageType() {
        return HKeysMessage;
    }
}
