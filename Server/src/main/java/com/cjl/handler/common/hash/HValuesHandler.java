package com.cjl.handler.common.hash;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.hashMessage.HValuesMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.Map;

public class HValuesHandler implements CommonHandler<HValuesMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(HValuesMessage msg) throws Exception {
        String name = msg.getName();
        CacheNode cacheNode = HbCache.search(name);
        if (cacheNode != null) {
            if (cacheNode.getData() instanceof Map) {
                Map<String, String> data = (Map<String, String>) cacheNode.getData();
                StringBuilder sb = new StringBuilder();
                for (String value : data.values()) {
                    sb.append(value + "\n");
                }
                return new ResponseMessage(ResultCode.SUCCESS_CODE, sb.toString());
            } else {
                return new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to map");
            }

        } else {
            return new ResponseMessage(ResultCode.FAILURE_CODE, "key not exist");
        }
    }
}
