package com.cjl.handler.common.set;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.setMessage.SAddMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.HashSet;
import java.util.Set;

public class SAddHandler implements CommonHandler<SAddMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(SAddMessage msg) throws Exception {
        String name = msg.getName();
        CacheNode search = HbCache.search(name);
        if(search != null){
            Set<String> data = (Set<String>) search.getData();
            for (String value : msg.getValues()) {
                data.add(value);
            }
        } else{
            Set<String> data = new HashSet<String>();
            for (String value : msg.getValues()) {
                data.add(value);
            }
            HbCache.add(new CacheNode(name, data));
        }
        return new ResponseMessage(ResultCode.SUCCESS_CODE, "OK");
    }
}
