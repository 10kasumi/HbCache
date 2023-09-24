package com.cjl.handler.common.list;

import com.cjl.config.ThreadConfig;
import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.listMessage.BRpopMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

public class BRpopHandler implements CommonHandler<BRpopMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(BRpopMessage msg) throws Exception {
        AtomicReference<ResponseMessage> message = new AtomicReference<>();
        CompletableFuture bRpopFuture = CompletableFuture.supplyAsync(() -> {
            String name = msg.getName();
            CacheNode search;
            while (true) {
                search = HbCache.search(name);
                if (search == null) {
                    continue;
                }
                if (search.getData() instanceof LinkedBlockingQueue){
                    LinkedBlockingDeque<String> data = (LinkedBlockingDeque<String>) search.getData();
                    if (data.size() > 0) {
                        message.set(new ResponseMessage(ResultCode.SUCCESS_CODE, data.pollLast()));
                    }
                } else{
                    message.set(new ResponseMessage(ResultCode.FAILURE_CODE, "can not cast value to list"));
                }
            }
        }, ThreadConfig.blockThreadPool);
        return message.get();
    }
}
