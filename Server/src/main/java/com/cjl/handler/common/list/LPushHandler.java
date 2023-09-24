package com.cjl.handler.common.list;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.listMessage.LPushMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.concurrent.LinkedBlockingDeque;

public class LPushHandler implements CommonHandler<LPushMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(LPushMessage msg) throws Exception {
        String name = msg.getName();
        CacheNode search = HbCache.search(name);
        String[] values = msg.getValues();
        if(search == null){
            LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
            for (String value : values) {
                deque.addFirst(value);
            }
            HbCache.add(new CacheNode(name, deque));
        } else {
            LinkedBlockingDeque<String> data = (LinkedBlockingDeque<String>) search.getData();
            for (String value : values) {
                data.addFirst(value);
            }
        }
       return new ResponseMessage(ResultCode.SUCCESS_CODE, "OK");
    }
}
