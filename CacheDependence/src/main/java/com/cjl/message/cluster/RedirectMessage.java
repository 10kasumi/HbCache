package com.cjl.message.cluster;

import com.cjl.message.AbstractResponseMessage;
import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RedirectMessage extends AbstractResponseMessage {

    private String host;

    private int port;

    private Message msg;


    public RedirectMessage(String host, int port, Message msg){
        this.host = host;
        this.port = port;
        this.msg = msg;
    }

    @Override
    public int getMessageType() {
        return RedirectMessage;
    }


}
