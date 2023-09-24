package com.cjl.message.setMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;


@Data
@ToString(callSuper = true)
public class SCardMessage extends Message {
    private String from;
    private String name;

    public SCardMessage() {
    }

    public SCardMessage(String name) {
        this.name = name;
    }

    @Override
    public int getMessageType() {
        return SCardMessage;
    }
}
