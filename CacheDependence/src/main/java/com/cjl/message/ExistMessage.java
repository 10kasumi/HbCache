package com.cjl.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class ExistMessage extends Message{
    private String name;

    public ExistMessage() {
    }

    public ExistMessage(String name) {
        this.name = name;
    }

    @Override
    public int getMessageType() {
        return ExistMessage;
    }
}
