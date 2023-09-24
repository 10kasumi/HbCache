package com.cjl.message.stringMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class SetExStringMessage extends Message {
    private String key;
    private String value;

    private Long expire;

    public SetExStringMessage() {
    }

    public SetExStringMessage(String key, String value, Long expire) {
        this.key = key;
        this.value = value;
        this.expire = expire;
    }

    @Override
    public int getMessageType() {
        return SetExStringMessage;
    }
}
