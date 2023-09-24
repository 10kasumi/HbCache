package com.cjl.handler.common.string;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.stringMessage.IncrStringMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

public class IncrStringHandler implements CommonHandler<IncrStringMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(IncrStringMessage msg) throws Exception {
        String key = msg.getKey();
        CacheNode cacheNode = HbCache.search(key);
        if(cacheNode == null){
            return new ResponseMessage(ResultCode.FAILURE_CODE, "key not exist");
        }
        String data = (String) cacheNode.getData();
        try {
            int val = Integer.parseInt(data) + 1;
            cacheNode.setData(val + "");
        } catch (NumberFormatException e) {
            return new ResponseMessage(ResultCode.FAILURE_CODE, "invalid format number");
        }
        return new ResponseMessage(ResultCode.SUCCESS_CODE,  "OK");
    }
}
