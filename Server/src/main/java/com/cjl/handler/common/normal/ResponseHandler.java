package com.cjl.handler.common.normal;

import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import lombok.Synchronized;

public class ResponseHandler implements CommonHandler<ResponseMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(ResponseMessage msg) throws Exception {
        return msg;
    }
}
