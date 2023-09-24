package com.cjl.message.hashMessage;

import com.cjl.message.Message;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class HValuesMessage extends Message {
    private String name;

    public HValuesMessage() {
    }

    public HValuesMessage(String name) {
        this.name = name;
    }

    @Override
    public int getMessageType() {
        return HValuesMessage;
    }
}
