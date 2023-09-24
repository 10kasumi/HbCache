package com.cjl.handler.common.normal;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ExpireMessage;
import com.cjl.message.ResponseMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

public class ExpireHandler implements CommonHandler<ExpireMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(ExpireMessage msg) throws Exception {
        CacheNode cacheNode = HbCache.search(msg.getName());
        ResponseMessage message;
        if(cacheNode != null){
            if(cacheNode.getExpire() != 0){
                message = new ResponseMessage(ResultCode.FAILURE_CODE, "can not update exist expire");
            } else{
                Long expire = System.currentTimeMillis() + msg.getExpire();
                cacheNode.setExpire(expire);
                message = new ResponseMessage(ResultCode.SUCCESS_CODE, "OK");
            }
        } else{
            message = new ResponseMessage(ResultCode.FAILURE_CODE, "No Such keys");
        }
        return message;
    }
}
