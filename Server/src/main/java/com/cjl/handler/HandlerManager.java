package com.cjl.handler;

import com.cjl.handler.common.hash.*;
import com.cjl.handler.common.list.*;
import com.cjl.handler.common.normal.*;
import com.cjl.handler.common.set.*;
import com.cjl.handler.common.string.*;
import com.cjl.message.Message;
import com.cjl.message.ResponseMessage;

import java.util.HashMap;
import java.util.Map;

public class HandlerManager {

    public static final Map<Integer, CommonHandler> handlerMap = new HashMap<>();

    static {
        handlerMap.put(Message.HelpMessage, new HelpHandler());
        handlerMap.put(Message.DeleteMessage, new DeleteHandler());
        handlerMap.put(Message.ExistMessage, new ExistHandler());
        handlerMap.put(Message.ExpireMessage, new ExpireHandler());
        handlerMap.put(Message.TtlMessage, new TtlHandler());

        handlerMap.put(Message.SetStringMessage, new SetStringHandler());
        handlerMap.put(Message.GetStringMessage, new GetStringHandler());
        handlerMap.put(Message.MSetStringMessage, new MSetStringHandler());
        handlerMap.put(Message.MGetStringMessage, new MGetStringHandler());
        handlerMap.put(Message.IncrStringMessage, new IncrByStringHandler());
        handlerMap.put(Message.IncrByStringMessage, new IncrByStringHandler());
        handlerMap.put(Message.SetNxStringMessage, new SetNxStringHandler());
        handlerMap.put(Message.SetExStringMessage, new SetExStringHandler());

        handlerMap.put(Message.HSetMessage, new HSetHandler());
        handlerMap.put(Message.HGetMessage, new HGetHandler());
        handlerMap.put(Message.HMSetMessage, new HMSetHandler());
        handlerMap.put(Message.HMGetMessage, new HMGetHandler());
        handlerMap.put(Message.HGetAllMessage, new HGetAllHandler());
        handlerMap.put(Message.HKeysMessage, new HKeysHandler());
        handlerMap.put(Message.HValuesMessage, new HValuesHandler());
        handlerMap.put(Message.HIncrByMessage, new HIncrByHandler());
        handlerMap.put(Message.HSetNxMessage, new HSetNxHandler());

        handlerMap.put(Message.LPushMessage, new LPushHandler());
        handlerMap.put(Message.LPopMessage, new LPopHandler());
        handlerMap.put(Message.RPushMessage, new RPushHandler());
        handlerMap.put(Message.RPopMessage, new RPopHandler());
        handlerMap.put(Message.BLpopMessage, new BLpopHandler());
        handlerMap.put(Message.BRpopMessage, new BRpopHandler());
        handlerMap.put(Message.LRangeMessage, new LRangeHandler());

        handlerMap.put(Message.SAddMessage, new SAddHandler());
        handlerMap.put(Message.SRemMessage, new SRemHandler());
        handlerMap.put(Message.SCardMessage, new SCardHandler());
        handlerMap.put(Message.SIsMemberMessage, new SIsMemberHandler());
        handlerMap.put(Message.SMembersMessage, new SMemberHandler());
    }

    public static ResponseMessage process(Message msg) throws Exception {
        CommonHandler commonHandler = handlerMap.get(msg.getMessageType());
        ResponseMessage message = commonHandler.process(msg);
        return message;
    }

}
