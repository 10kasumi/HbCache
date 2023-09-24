package com.cjl.message.listMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;


@Data
@ToString(callSuper = true)
public class BLpopMessage extends Message {
    private String name;

    public BLpopMessage() {
    }

    public BLpopMessage(String name) {
        this.name = name;
    }

    @Override
    public int getMessageType() {
        return BLpopMessage;
    }
}
