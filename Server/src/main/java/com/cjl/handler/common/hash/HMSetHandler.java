package com.cjl.handler.common.hash;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.hashMessage.HMSetMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.HashMap;
import java.util.Map;

public class HMSetHandler implements CommonHandler<HMSetMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(HMSetMessage msg) throws Exception {
        String name = msg.getName();
        CacheNode cacheNode = HbCache.search(name);
        String[] keys = msg.getKeys();
        String[] values = msg.getValues();
        if(cacheNode == null){
            CacheNode node = new CacheNode();
            node.setName(msg.getName());
            Map<String, String> data = new HashMap<>();
            for(int i = 0; i < keys.length; i++){
                data.put(keys[i], values[i]);
            }
            node.setData(data);
            HbCache.add(node);
        } else{
            Map<String, String> data = (Map<String, String>) cacheNode.getData();
            for(int i = 0; i < keys.length; i++){
                data.put(keys[i], values[i]);
            }
        }
        return new ResponseMessage(ResultCode.SUCCESS_CODE, "OK");
    }
}
