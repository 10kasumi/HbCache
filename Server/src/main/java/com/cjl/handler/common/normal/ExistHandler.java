package com.cjl.handler.common.normal;


import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ExistMessage;
import com.cjl.message.ResponseMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

public class ExistHandler implements CommonHandler<ExistMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(ExistMessage msg) throws Exception {
        String name = msg.getName();
        ResponseMessage message;
        CacheNode search = HbCache.search(name);
        if (search != null) {
            message = new ResponseMessage(ResultCode.SUCCESS_CODE, "key exists");
        } else {
            message = new ResponseMessage(ResultCode.SUCCESS_CODE, "key not exist");
        }
        return message;
    }
}
