package com.cjl.message.hashMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class HSetMessage extends Message {
    private String name;
    private String key;
    private String value;

    @Override
    public int getMessageType() {
        return HSetMessage;
    }
}
