package com.cjl.message.listMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class RPushMessage extends Message {
    private String name;
    private String[] values;

    @Override
    public int getMessageType() {
        return RPushMessage;
    }
}
