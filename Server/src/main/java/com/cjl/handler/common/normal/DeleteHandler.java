package com.cjl.handler.common.normal;

import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.DeleteMessage;
import com.cjl.message.ResponseMessage;
import com.cjl.server.store.HbCache;
import lombok.Synchronized;

public class DeleteHandler implements CommonHandler<DeleteMessage> {
    @Synchronized
    @Override
    public ResponseMessage process( DeleteMessage msg) throws Exception {
        String key = msg.getKey();
        ResponseMessage responseMessage;
        HbCache.delete(key);
        responseMessage = new ResponseMessage(ResultCode.SUCCESS_CODE, "delete success");
        return responseMessage;
    }
}
