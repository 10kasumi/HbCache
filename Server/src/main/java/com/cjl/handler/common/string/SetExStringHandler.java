package com.cjl.handler.common.string;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.stringMessage.SetExStringMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

public class SetExStringHandler implements CommonHandler<SetExStringMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(SetExStringMessage msg) throws Exception {
        String key = msg.getKey();
        CacheNode cacheNode = HbCache.search(key);
        if(cacheNode == null){
            CacheNode node = new CacheNode(key, msg.getValue());
            node.setExpire(System.currentTimeMillis() + msg.getExpire());
            HbCache.add(node);
        }
        return new ResponseMessage(ResultCode.SUCCESS_CODE, "OK");
    }
}
