package com.cjl.message.setMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class SMembersMessage extends Message {
    private String from;
    private String name;

    public SMembersMessage() {
    }

    public SMembersMessage(String name) {
        this.name = name;
    }

    @Override
    public int getMessageType() {
        return SMembersMessage;
    }
}
