package com.cjl.handler.common.set;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.setMessage.SRemMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.Set;

public class SRemHandler implements CommonHandler<SRemMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(SRemMessage msg) throws Exception {
        String name = msg.getName();
        CacheNode search = HbCache.search(name);
        if (search == null) {
            return new ResponseMessage(ResultCode.FAILURE_CODE, "key not exist");

        }
        if(search.getData() instanceof Set){
            Set<String> data = (Set<String>) search.getData();
            for (String value : msg.getValues()) {
                data.remove(value);
            }
            return new ResponseMessage(ResultCode.SUCCESS_CODE, "OK");
        } else{
            return new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to set");
        }
    }
}
