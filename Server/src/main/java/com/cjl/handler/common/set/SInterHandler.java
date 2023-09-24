package com.cjl.handler.common.set;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.setMessage.SInterMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.Set;

public class SInterHandler implements CommonHandler<SInterMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(SInterMessage msg) throws Exception {
        String key1 = msg.getKey1();
        String key2 = msg.getKey2();
        CacheNode node1 = HbCache.search(key1);
        CacheNode node2 = HbCache.search(key2);
        if(node1.getData() instanceof Set && node2.getData() instanceof Set){
            Set<String> value1 = (Set<String>) node1.getData();
            Set<String> value2 = (Set<String>) node2.getData();
            StringBuilder sb = new StringBuilder();
            for(String value : value1){
                if(value2.contains(value)){
                    sb.append(value + "\n");
                }
            }
            return new ResponseMessage(ResultCode.SUCCESS_CODE, sb.toString());
        } else{
            return new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to set");
        }
    }
}
