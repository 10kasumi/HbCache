package com.cjl.handler;

import com.cjl.message.Message;
import com.cjl.message.ResponseMessage;

public interface CommonHandler<T extends Message> {

    ResponseMessage process(T msg) throws Exception;
}
