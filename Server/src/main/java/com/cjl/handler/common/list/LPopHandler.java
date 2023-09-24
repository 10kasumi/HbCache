package com.cjl.handler.common.list;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.listMessage.LPopMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class LPopHandler implements CommonHandler<LPopMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(LPopMessage msg) throws Exception {
        String name = msg.getName();
        CacheNode search = HbCache.search(name);
        if (search == null) {
            return new ResponseMessage(ResultCode.FAILURE_CODE, "key not exist");
        }
        if (search.getData() instanceof LinkedBlockingQueue){
            LinkedBlockingDeque<String> data = (LinkedBlockingDeque<String>) search.getData();
            if (data.size() > 0) {
                return new ResponseMessage(ResultCode.SUCCESS_CODE, data.pollFirst());
            } else{
                return new ResponseMessage(ResultCode.SUCCESS_CODE, "empty list");
            }
        } else{
            return new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to list");
        }
    }
}
