package com.cjl.handler.common.string;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.ResponseMessage;
import com.cjl.message.stringMessage.MSetStringMessage;
import com.cjl.server.store.CacheNode;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

public class MSetStringHandler implements CommonHandler<MSetStringMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(MSetStringMessage msg) throws Exception {
        String[] keys = msg.getKeys();
        String[] values = msg.getValues();
        for(int i = 0; i < keys.length; i++){
            CacheNode search = HbCache.search(keys[i]);
            if(search != null){
                search.setData(values[i]);
            } else{
                HbCache.add(new CacheNode(keys[i], values[i]));
            }
        }
        return new ResponseMessage(ResultCode.SUCCESS_CODE,  "OK");
    }
}
