package com.cjl.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class TtlMessage extends Message{
    private String name;

    public TtlMessage() {
    }

    public TtlMessage(String name) {
        this.name = name;
    }

    @Override
    public int getMessageType() {
        return TtlMessage;
    }
}
