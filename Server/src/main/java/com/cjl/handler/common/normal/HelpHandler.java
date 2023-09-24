package com.cjl.handler.common.normal;

import com.cjl.constrants.CommandConstrants;
import com.cjl.constrants.ResultCode;
import com.cjl.handler.CommonHandler;
import com.cjl.message.HelpMessage;
import com.cjl.message.ResponseMessage;
import lombok.Synchronized;

import java.util.List;

public class HelpHandler implements CommonHandler<HelpMessage> {
    @Synchronized
    @Override
    public ResponseMessage process(HelpMessage msg) throws Exception {
        List<String> list = CommandConstrants.list;
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s + "\n");
        }
        String result = sb.toString();
        ResponseMessage message = new ResponseMessage(ResultCode.SUCCESS_CODE, result);
        return message;
    }
}
