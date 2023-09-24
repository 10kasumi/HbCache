package com.cjl.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResponseMessage extends AbstractResponseMessage{
    private int code;

    private String result;

    private String errorMessage;

    public ResponseMessage() {
    }

    public ResponseMessage(int code, String result) {
        this.code = code;
        this.result = result;
    }

    public ResponseMessage(int code, String result, String errorMessage) {
        this.code = code;
        this.result = result;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getMessageType() {
        return ResponseMessage;
    }
}
