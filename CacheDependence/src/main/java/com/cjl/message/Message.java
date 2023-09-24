package com.cjl.message;

import com.cjl.message.cluster.VoteResponseMessage;
import com.cjl.message.hashMessage.*;
import com.cjl.message.listMessage.*;
import com.cjl.message.setMessage.*;
import com.cjl.message.stringMessage.*;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Message implements Serializable {

    /**
     * 根据消息类型字节，获得对应的消息 class
     *
     * @param messageType 消息类型字节
     * @return 消息 class
     */
    public static Class<? extends Message> getMessageClass(int messageType) {
        return messageClasses.get(messageType);
    }

    private int messageType;

    private int sequenceId;

    public abstract int getMessageType();

    public static final int HelpMessage = 100;
    public static final int DeleteMessage = 101;
    public static final int ExistMessage = 102;
    public static final int ExpireMessage = 103;
    public static final int TtlMessage = 104;
    public static final int ResponseMessage = 105;
    public static final int LoginRequestMessage = 106;
    public static final int LoginResponseMessage = 107;

    public static final int SetStringMessage = 200;
    public static final int GetStringMessage = 201;
    public static final int MSetStringMessage = 202;
    public static final int MGetStringMessage = 203;
    public static final int IncrStringMessage = 204;
    public static final int IncrByStringMessage = 205;
    public static final int SetNxStringMessage = 206;
    public static final int SetExStringMessage = 207;

    public static final int HSetMessage = 300;
    public static final int HGetMessage = 301;
    public static final int HMSetMessage = 302;
    public static final int HMGetMessage = 303;
    public static final int HGetAllMessage = 304;
    public static final int HKeysMessage = 305;
    public static final int HValuesMessage = 306;
    public static final int HIncrByMessage = 307;
    public static final int HSetNxMessage = 308;

    public static final int LPushMessage = 400;
    public static final int LPopMessage = 401;
    public static final int RPushMessage = 402;
    public static final int RPopMessage = 403;
    public static final int BLpopMessage = 404;
    public static final int BRpopMessage = 405;
    public static final int LRangeMessage = 406;

    public static final int SAddMessage = 500;
    public static final int SRemMessage = 501;
    public static final int SCardMessage = 502;
    public static final int SIsMemberMessage = 503;
    public static final int SMembersMessage = 504;
    public static final int SInterMessage = 505;
    public static final int SDiffMessage = 506;
    public static final int SUnionMessage = 507;

    public static final int RedirectMessage = 600;
    public static final int CheckAliveMessage = 601;

    public static final int VoteRequestMessage = 700;
    public static final int VoteResponseMessage = 701;
    public static final int SetMasterNodeMessage = 702;
    public static final int GetMasterNodeMessage = 703;
    public static final int ReturnMasterNodeMessage = 704;

    private static final Map<Integer, Class<? extends Message>> messageClasses = new HashMap<>();

    static {
        messageClasses.put(HelpMessage, HelpMessage.class);
        messageClasses.put(DeleteMessage, DeleteMessage.class);
        messageClasses.put(ExistMessage, ExistMessage.class);
        messageClasses.put(ExpireMessage, ExpireMessage.class);
        messageClasses.put(TtlMessage, TtlMessage.class);
        messageClasses.put(ResponseMessage, ResponseMessage.class);
        messageClasses.put(LoginRequestMessage, LoginRequestMessage.class);
        messageClasses.put(LoginResponseMessage, LoginResponseMessage.class);

        messageClasses.put(SetStringMessage, SetStringMessage.class);
        messageClasses.put(GetStringMessage, GetStringMessage.class);
        messageClasses.put(MSetStringMessage, MSetStringMessage.class);
        messageClasses.put(MGetStringMessage, MGetStringMessage.class);
        messageClasses.put(IncrStringMessage, IncrStringMessage.class);
        messageClasses.put(IncrByStringMessage, IncrByStringMessage.class);
        messageClasses.put(SetNxStringMessage, SetNxStringMessage.class);
        messageClasses.put(SetExStringMessage, SetExStringMessage.class);

        messageClasses.put(HSetMessage, HSetMessage.class);
        messageClasses.put(HGetMessage, HGetMessage.class);
        messageClasses.put(HMSetMessage, HMSetMessage.class);
        messageClasses.put(HMGetMessage, HMGetMessage.class);
        messageClasses.put(HGetAllMessage, HGetAllMessage.class);
        messageClasses.put(HKeysMessage, HKeysMessage.class);
        messageClasses.put(HValuesMessage, HValuesMessage.class);
        messageClasses.put(HIncrByMessage, HIncrByMessage.class);
        messageClasses.put(HSetNxMessage, HSetNxMessage.class);

        messageClasses.put(LPushMessage, LPushMessage.class);
        messageClasses.put(LPopMessage, LPopMessage.class);
        messageClasses.put(RPushMessage, RPushMessage.class);
        messageClasses.put(RPopMessage, RPopMessage.class);
        messageClasses.put(BLpopMessage, BLpopMessage.class);
        messageClasses.put(BRpopMessage, BRpopMessage.class);
        messageClasses.put(LRangeMessage, LRangeMessage.class);

        messageClasses.put(SAddMessage, SAddMessage.class);
        messageClasses.put(SRemMessage, SRemMessage.class);
        messageClasses.put(SCardMessage, SCardMessage.class);
        messageClasses.put(SIsMemberMessage, SIsMemberMessage.class);
        messageClasses.put(SMembersMessage, SMembersMessage.class);

        messageClasses.put(RedirectMessage, com.cjl.message.cluster.RedirectMessage.class);
        messageClasses.put(CheckAliveMessage, com.cjl.message.cluster.CheckAliveMessage.class);
        messageClasses.put(VoteRequestMessage, com.cjl.message.cluster.VoteRequestMessage.class);
        messageClasses.put(VoteResponseMessage, VoteResponseMessage.class);
        messageClasses.put(SetMasterNodeMessage, com.cjl.message.cluster.SetMasterNodeMessage.class);
        messageClasses.put(GetMasterNodeMessage, com.cjl.message.cluster.GetMasterNodeMessage.class);
        messageClasses.put(ReturnMasterNodeMessage, com.cjl.message.cluster.ReturnMasterNodeMessage.class);
    }

}

