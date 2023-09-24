package com.cjl.message.setMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;


@Data
@ToString(callSuper = true)
public class SIsMemberMessage extends Message {
    private String from;
    private String name;
    private String value;

    public SIsMemberMessage() {
    }

    public SIsMemberMessage(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public int getMessageType() {
        return SIsMemberMessage;
    }
}
