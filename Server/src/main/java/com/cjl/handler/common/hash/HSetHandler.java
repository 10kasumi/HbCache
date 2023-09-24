package com.cjl.handler.common.hash;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.hashMessage.HSetMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.HashMap;
import java.util.Map;

public class HSetHandler implements CommonHandler<HSetMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(HSetMessage msg) throws Exception {
        String name = msg.getName();
        CacheNode cacheNode = HbCache.search(name);
        String key = msg.getKey();
        String value = msg.getValue();
        if(cacheNode == null){
            Map<String, String> data = new HashMap<>();
            data.put(key, value);
            CacheNode node = new CacheNode();
            node.setName(msg.getName());
            node.setData(data);
            HbCache.add(node);
        } else{
            Map<String, String> data = (Map<String, String>) cacheNode.getData();
            data.put(key, value);
        }
        return new ResponseMessage(ResultCode.SUCCESS_CODE, "OK");
    }
}
