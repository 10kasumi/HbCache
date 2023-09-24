package com.cjl.handler.common.list;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.listMessage.LRangeMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class LRangeHandler implements CommonHandler<LRangeMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(LRangeMessage msg) throws Exception {
        String name = msg.getName();
        int end = msg.getEnd();
        int start = msg.getStart();
        CacheNode search = HbCache.search(name);
        if (search.getData() instanceof LinkedBlockingQueue) {
            LinkedBlockingDeque<String> data = (LinkedBlockingDeque<String>) search.getData();
            if (data.size() == 0) {
                return new ResponseMessage(ResultCode.SUCCESS_CODE, "empty list");
            }
            if (end == -1 || end >= data.size()) {
                end = data.size();
            }
            StringBuilder sb = new StringBuilder();
            String[] res = data.toArray(new String[data.size()]);
            for (int i = start; i < end; i++) {
                sb.append(res[i] + " ");
            }
            return new ResponseMessage(ResultCode.SUCCESS_CODE, sb.toString());
        } else {
            return new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to list");
        }
    }
}
