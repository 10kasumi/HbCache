package com.cjl.handler.common.set;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.setMessage.SMembersMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.Set;

public class SMemberHandler implements CommonHandler<SMembersMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(SMembersMessage msg) throws Exception {
        String name = msg.getName();
        CacheNode search = HbCache.search(name);
        if (search == null) {
            return new ResponseMessage(ResultCode.FAILURE_CODE, "key not exist");
        }
        if(search.getData() instanceof Set){
            StringBuilder sb = new StringBuilder();
            for (String str : (Set<String>) search.getData()) {
                sb.append(str + "\n");
            }
            return new ResponseMessage(ResultCode.SUCCESS_CODE, sb.toString());
        } else{
            return new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to set");
        }
    }
}
