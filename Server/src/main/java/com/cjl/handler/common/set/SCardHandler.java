package com.cjl.handler.common.set;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.setMessage.SCardMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.Set;

public class SCardHandler implements CommonHandler<SCardMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(SCardMessage msg) throws Exception {
        String name = msg.getName();
        CacheNode search = HbCache.search(name);
        if(search == null){
            return new ResponseMessage(ResultCode.FAILURE_CODE, "key not exist");
        }
        if(search.getData() instanceof  Set){
            Set<String> data = (Set<String>) search.getData();
            int ans = data.size();

            return new ResponseMessage(ResultCode.SUCCESS_CODE, ans + "");
        } else{
            return new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to set");
        }
    }
}
