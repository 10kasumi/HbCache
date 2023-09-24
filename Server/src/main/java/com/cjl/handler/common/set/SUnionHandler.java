package com.cjl.handler.common.set;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.setMessage.SUnionMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.HashSet;
import java.util.Set;

public class SUnionHandler implements CommonHandler<SUnionMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(SUnionMessage msg) throws Exception {
        CacheNode node1 = HbCache.search(msg.getKey1());
        CacheNode node2 = HbCache.search(msg.getKey2());
        if(node1.getData() instanceof Set && node2.getData() instanceof Set){
            Set<String> value1 = (Set<String>) node1.getData();
            Set<String> value2 = (Set<String>) node2.getData();
            Set<String> res = new HashSet<>();
            res.addAll(value1);
            res.addAll(value2);
            StringBuilder sb = new StringBuilder();
            for(String str : res){
                sb.append(str + "\n");
            }
            return new ResponseMessage(ResultCode.SUCCESS_CODE, sb.toString());
        } else{
            return new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to set");
        }
    }
}
