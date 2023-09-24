package com.cjl.handler.common.hash;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.hashMessage.HKeysMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.Map;

public class HKeysHandler implements CommonHandler<HKeysMessage> {
    @Synchronized
    @Override
    public ResponseMessage process( HKeysMessage msg) throws Exception {
        String name = msg.getName();
        CacheNode cacheNode = HbCache.search(name);
        StringBuilder sb = new StringBuilder();
        ResponseMessage message = new ResponseMessage();
        if(cacheNode != null){
            if(cacheNode.getData() instanceof Map){
                Map<String, String> map = (Map<String, String>) cacheNode.getData();
                for (String key : map.keySet()) {
                    sb.append(key + "\n");
                }
                message.setResult(sb.toString());
                message.setCode(ResultCode.SUCCESS_CODE);
            } else{
                message = new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to map");
            }
        } else{
            message.setCode(ResultCode.FAILURE_CODE);
            message.setErrorMessage("key not exist");
        }
        return message;
    }
}
