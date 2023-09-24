package com.cjl.handler.common.set;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.setMessage.SDiffMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.Set;

public class SDiffHandler implements CommonHandler<SDiffMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(SDiffMessage msg) throws Exception {
        StringBuilder sb = new StringBuilder();
        CacheNode node1 = HbCache.search(msg.getKey1());
        CacheNode node2 = HbCache.search(msg.getKey2());
        if((node1 instanceof Set) && (node2 instanceof Set)){
            Set<String> value1 = (Set<String>) node1;
            Set<String> value2 = (Set<String>) node2;
            for(String str : value1){
                if(!value2.contains(str)){
                    sb.append(str + "\n");
                }
            }
            return new ResponseMessage(ResultCode.SUCCESS_CODE, sb.toString());
        }
        return new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to set");
    }
}
