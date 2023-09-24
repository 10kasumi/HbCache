package com.cjl.message.hashMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;


@Data
@ToString(callSuper = true)
public class HIncrByMessage extends Message {
    private String name;

    private String key;

    private Integer step;

    @Override
    public int getMessageType() {
        return HIncrByMessage;
    }
}
