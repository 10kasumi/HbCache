package com.cjl.handler.common.hash;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.hashMessage.HGetMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.Map;

public class HGetHandler implements CommonHandler<HGetMessage> {

    @Synchronized
    @Override
    public ResponseMessage process(HGetMessage msg) throws Exception {
        String name = msg.getName();
        ResponseMessage message;
        CacheNode cacheNode = HbCache.search(name);
        if (cacheNode != null) {
            Object nodeData = cacheNode.getData();
            if (nodeData instanceof Map) {
                Map<String, String> data = (Map<String, String>) nodeData;
                String result = data.get(msg.getKey());
                if (result == null) {
                    message = new ResponseMessage(ResultCode.FAILURE_CODE, "field not exist");
                } else {
                    message = new ResponseMessage(ResultCode.SUCCESS_CODE, result);
                }
            } else{
                message = new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to map");
            }
        } else {
            message = new ResponseMessage(ResultCode.FAILURE_CODE, "key not exist");
        }
        return message;
    }
}
