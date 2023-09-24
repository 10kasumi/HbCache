package com.cjl.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class DeleteMessage extends Message{
    private String key;

    public DeleteMessage() {
    }

    public DeleteMessage(String key) {
        this.key = key;
    }

    @Override
    public int getMessageType() {
        return DeleteMessage;
    }
}
