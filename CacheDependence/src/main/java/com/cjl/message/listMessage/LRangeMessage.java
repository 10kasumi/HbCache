package com.cjl.message.listMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class LRangeMessage extends Message {
    private String name;

    private int start;

    private int end;

    public LRangeMessage(String name, int start, int end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    @Override
    public int getMessageType() {
        return LRangeMessage;
    }
}
