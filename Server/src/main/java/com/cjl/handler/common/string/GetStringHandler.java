package com.cjl.handler.common.string;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.stringMessage.GetStringMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

public class GetStringHandler implements CommonHandler<GetStringMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(GetStringMessage msg) throws Exception {
        String key = msg.getKey();
        CacheNode cacheNode = HbCache.search(key);
        if(cacheNode == null){
            return new ResponseMessage(ResultCode.FAILURE_CODE, "key not exist");
        }
        if(cacheNode.getData() instanceof String){
            return new ResponseMessage(ResultCode.SUCCESS_CODE, (String) cacheNode.getData());
        } else{
            return new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to string");
        }
    }
}
