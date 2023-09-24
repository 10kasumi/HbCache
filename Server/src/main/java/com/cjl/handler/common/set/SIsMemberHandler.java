package com.cjl.handler.common.set;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.setMessage.SIsMemberMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.Set;

public class SIsMemberHandler implements CommonHandler<SIsMemberMessage> {

    @Synchronized
    @Override
    public ResponseMessage process(SIsMemberMessage msg) throws Exception {
        String name = msg.getName();
        CacheNode search = HbCache.search(name);
        if (search == null) {
            return new ResponseMessage(ResultCode.FAILURE_CODE, "key not exist");
        }
        if (search.getData() instanceof Set) {
            Set<String> data = (Set<String>) search.getData();
            if (data.contains(msg.getValue())) {
                return new ResponseMessage(ResultCode.SUCCESS_CODE, "exist");
            } else {
                return new ResponseMessage(ResultCode.SUCCESS_CODE, "not exist");
            }
        } else {
            return new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to set");
        }
    }
}
