package com.cjl.handler.common.string;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.stringMessage.IncrByStringMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;

public class IncrByStringHandler implements CommonHandler<IncrByStringMessage> {
    @Override
    public ResponseMessage process(IncrByStringMessage msg) throws Exception {
        String key = msg.getKey();
        CacheNode cacheNode = HbCache.search(key);
        if(cacheNode == null){
            return new ResponseMessage(ResultCode.FAILURE_CODE,  "key not exist");

        }
        String data = (String) cacheNode.getData();
        int step = msg.getStep();
        try {
            int val = step + Integer.parseInt(data);
            cacheNode.setData(val + "");
        } catch (NumberFormatException e) {
            return new ResponseMessage(ResultCode.FAILURE_CODE, "invalid format number");

        }
        return new ResponseMessage(ResultCode.SUCCESS_CODE, "OK");
    }
}
