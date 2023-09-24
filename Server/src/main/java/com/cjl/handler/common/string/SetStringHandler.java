package com.cjl.handler.common.string;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.stringMessage.SetStringMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

public class SetStringHandler implements CommonHandler<SetStringMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(SetStringMessage msg) throws Exception {
        String key = msg.getKey();
        CacheNode search = HbCache.search(key);
        if(search != null){
            search.setData(msg.getValue());
        }
        HbCache.add(new CacheNode(key, msg.getValue()));
//        channel.writeAndFlush(new ResponseMessage(ResultCode.SUCCESS_CODE, "OK"));
        return new ResponseMessage(ResultCode.SUCCESS_CODE, "OK");
    }
}
