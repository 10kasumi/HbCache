package com.cjl.handler.common.normal;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.TtlMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

public class TtlHandler implements CommonHandler<TtlMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(TtlMessage msg) throws Exception {
        CacheNode cacheNode = HbCache.search(msg.getName());
        ResponseMessage message;
        if(cacheNode != null){
            if(cacheNode.getExpire() != 0){
                long ttl = cacheNode.getExpire() - System.currentTimeMillis();
                message = new ResponseMessage(ResultCode.SUCCESS_CODE, "ttl time: " + ttl);
            } else{
                message = new ResponseMessage(ResultCode.FAILURE_CODE, "key not exist expire time");
            }
        } else{
            message = new ResponseMessage(ResultCode.FAILURE_CODE, "No Such keys");
        }
        return message;
    }
}
