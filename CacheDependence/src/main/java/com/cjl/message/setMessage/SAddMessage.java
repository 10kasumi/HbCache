package com.cjl.message.setMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class SAddMessage extends Message {
    private String from;

    private String name;

    private String[] values;
    @Override
    public int getMessageType() {
        return SAddMessage;
    }
}
