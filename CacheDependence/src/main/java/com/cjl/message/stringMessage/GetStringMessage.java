package com.cjl.message.stringMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GetStringMessage extends Message {
    private String key;

    public GetStringMessage() {
    }

    public GetStringMessage(String key) {
        this.key = key;

    }

    @Override
    public int getMessageType() {
        return GetStringMessage;
    }
}
