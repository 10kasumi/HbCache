package com.cjl.message.stringMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;


@Data
@ToString(callSuper = true)
public class SetNxStringMessage extends Message {
    private String key;

    private String value;

    public SetNxStringMessage() {
    }

    public SetNxStringMessage(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int getMessageType() {
        return SetNxStringMessage;
    }
}
