package com.cjl.message;


import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class ExpireMessage extends Message{
    private String name;
    private Long expire;
    @Override
    public int getMessageType() {
        return ExpireMessage;
    }
}
