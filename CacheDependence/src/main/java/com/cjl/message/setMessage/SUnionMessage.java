package com.cjl.message.setMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class SUnionMessage extends Message {

    private String key1;
    private String key2;

    public SUnionMessage(String key1, String key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    @Override
    public int getMessageType() {
        return SUnionMessage;
    }
}
