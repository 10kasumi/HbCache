package com.cjl.handler.common.string;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.stringMessage.MGetStringMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

public class MGetStringHandler implements CommonHandler<MGetStringMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(MGetStringMessage msg) throws Exception {
        String[] keys = msg.getKeys();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < keys.length; i++){
            CacheNode cacheNode = HbCache.search(keys[i]);
            if(cacheNode == null){
                return new ResponseMessage(ResultCode.FAILURE_CODE, "key " + keys[i] + " not exist");
            }
            if(cacheNode.getData() instanceof String){
                sb.append((String) cacheNode.getData() + "\n");
            } else{
                return new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to string");
            }
        }

        return new ResponseMessage(ResultCode.SUCCESS_CODE, sb.toString());
    }
}
