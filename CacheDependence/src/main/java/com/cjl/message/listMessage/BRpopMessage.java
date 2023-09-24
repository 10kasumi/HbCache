package com.cjl.message.listMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class BRpopMessage extends Message {
    private String name;

    public BRpopMessage() {
    }

    public BRpopMessage(String name) {
        this.name = name;
    }

    @Override
    public int getMessageType() {
        return BRpopMessage;
    }
}
