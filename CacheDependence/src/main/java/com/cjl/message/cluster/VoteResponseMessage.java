package com.cjl.message.cluster;

import com.cjl.message.Message;
import lombok.Data;

@Data
public class VoteResponseMessage extends Message {
    private int code;

    public VoteResponseMessage(int code){
        this.code = code;
    }



    @Override
    public int getMessageType() {
        return VoteResponseMessage;
    }
}
