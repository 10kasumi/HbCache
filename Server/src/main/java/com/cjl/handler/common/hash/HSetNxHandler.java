package com.cjl.handler.common.hash;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.hashMessage.HSetNxMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.HashMap;
import java.util.Map;

public class HSetNxHandler implements CommonHandler<HSetNxMessage> {

    @Synchronized
    @Override
    public ResponseMessage process(HSetNxMessage msg) throws Exception {

        String name = msg.getName();
        CacheNode cacheNode = HbCache.search(name);
        String key = msg.getKey();
        String value = msg.getValue();
        if (cacheNode == null) {
            CacheNode node = new CacheNode();
            node.setName(msg.getName());
            Map<String, String> data = new HashMap<>();
            data.putIfAbsent(key, value);
            node.setData(data);
            HbCache.add(node);
        } else {
            Map<String, String> data = (Map<String, String>) cacheNode.getData();
            if(!data.containsKey(key)){
                data.put(key, value);
            }
        }
        return new ResponseMessage(ResultCode.SUCCESS_CODE, "OK");
    }
}
