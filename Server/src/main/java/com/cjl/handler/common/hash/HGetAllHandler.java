package com.cjl.handler.common.hash;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.hashMessage.HGetAllMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.Map;

public class HGetAllHandler implements CommonHandler<HGetAllMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(HGetAllMessage msg){
        String name = msg.getName();
        CacheNode cacheNode = HbCache.search(name);
        StringBuilder sb = new StringBuilder();
        ResponseMessage message;
        if(cacheNode != null){
            Object data = cacheNode.getData();
            if(data instanceof Map){
                Map<String, String> map = (Map<String, String>) data;
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    sb.append(entry.getKey() + ": " + entry.getValue() + "\n");
                }
                message = new ResponseMessage(ResultCode.SUCCESS_CODE, sb.toString());
            } else{
                message = new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to map");
            }
        } else{
            message = new ResponseMessage(ResultCode.FAILURE_CODE, "key not exist");
        }
        return message;
    }
}
