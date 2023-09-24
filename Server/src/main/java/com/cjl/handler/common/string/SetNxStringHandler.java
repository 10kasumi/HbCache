package com.cjl.handler.common.string;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.stringMessage.SetNxStringMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

public class SetNxStringHandler implements CommonHandler<SetNxStringMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(SetNxStringMessage msg) throws Exception {
        String key = msg.getKey();
        CacheNode cacheNode = HbCache.search(key);
        if(cacheNode == null){
            HbCache.add(new CacheNode(key, msg.getValue()));
        }
        return new ResponseMessage(ResultCode.SUCCESS_CODE, "OK");
    }
}
