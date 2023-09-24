package com.cjl.handler.common.hash;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.hashMessage.HIncrByMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.Map;

public class HIncrByHandler implements CommonHandler<HIncrByMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(HIncrByMessage msg) throws Exception {
        String name = msg.getName();
        ResponseMessage message;
        CacheNode cacheNode = HbCache.search(name);
        if(cacheNode != null){
            if(cacheNode.getData() instanceof Map){
                Map<String, String> data = (Map<String, String>) cacheNode.getData();
                String result = data.get(msg.getKey());
                if(result == null){
                    message = new ResponseMessage(ResultCode.FAILURE_CODE,  "field not exist");
                } else{
                    try {
                        int val = Integer.parseInt(result) + msg.getStep();
                        data.put(msg.getKey(), val + "");
                        message = new ResponseMessage(ResultCode.SUCCESS_CODE, "OK");
                    } catch (NumberFormatException e) {
                        message = new ResponseMessage(ResultCode.FAILURE_CODE, "invalid format number");
                        return message;
                    }
                }
            } else{
                message = new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to map");
            }

        } else{
            message = new ResponseMessage(ResultCode.FAILURE_CODE,  "key not exist");
        }

        return message;
    }
}
