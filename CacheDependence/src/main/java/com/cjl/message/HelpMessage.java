package com.cjl.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class HelpMessage extends Message{
    @Override
    public int getMessageType() {
        return HelpMessage;
    }
}
