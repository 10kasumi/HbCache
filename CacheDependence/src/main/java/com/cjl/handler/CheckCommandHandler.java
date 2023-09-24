package com.cjl.handler;

import com.cjl.constrants.ResultCode;
import com.cjl.message.*;
import com.cjl.message.hashMessage.*;
import com.cjl.message.listMessage.*;
import com.cjl.message.setMessage.*;
import com.cjl.message.stringMessage.*;
import io.netty.channel.Channel;
import com.cjl.message.Message;

public class CheckCommandHandler {

    private boolean isNotBlank(String s) {
        if (s == null || "".equals(s)) {
            return false;
        }
        return true;
    }

    public Message checkCommand(String command, Channel channel) {
        if (command == null || "".equals(command)) {
            return null;
        }
        String[] s = command.trim().split(" ");
        switch (s[0].toLowerCase()) {
            case "help":
                if (s.length == 1) {
                    HelpMessage helpMessage = new HelpMessage();
                        if (channel != null)
                            channel.writeAndFlush(helpMessage);
                    return helpMessage;
                }

            case "delete":
                if (s.length == 2 && isNotBlank(s[1])) {
                    DeleteMessage deleteMessage = new DeleteMessage(s[1]);

                        if (channel != null)
                            channel.writeAndFlush(deleteMessage);
                    return deleteMessage;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: delete key");

                        if (channel != null)
                            channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "exist":
                if (s.length == 2 && isNotBlank(s[1])) {
                    ExistMessage existMessage = new ExistMessage(s[1]);

                        if (channel != null)
                            channel.writeAndFlush(existMessage);
                    return existMessage;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: exist key");

                        if (channel != null)
                            channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "expire":
                if (s.length != 3) {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: expire key time");

                        if (channel != null)
                            channel.writeAndFlush(responseMessage);
                    return responseMessage;

                }
                if (!isNotBlank(s[1]) && !isNotBlank(s[2])) {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "missing parameter");

                        if (channel != null)
                            channel.writeAndFlush(responseMessage);
                    return responseMessage;

                }
                ExpireMessage expireMessage = new ExpireMessage();
                try {
                    long l = Long.parseLong(s[2]);
                    expireMessage.setName(s[1]);
                    expireMessage.setExpire(l);
                } catch (NumberFormatException e) {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "param time can not convert to numbser");
                        if (channel != null)
                            channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

                    if (channel != null)
                        channel.writeAndFlush(expireMessage);
                return expireMessage;

            case "ttl":
                if (s.length == 2 && isNotBlank(s[1])) {
                    TtlMessage ttlMessage = new TtlMessage(s[1]);

                        if (channel != null)
                            channel.writeAndFlush(ttlMessage);
                    return ttlMessage;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: ttl key");

                        if (channel != null)
                            channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "get":
                if (s.length == 2 && isNotBlank(s[1])) {
                    GetStringMessage getStringMessage = new GetStringMessage(s[1]);
                    if (channel != null)
                        channel.writeAndFlush(getStringMessage);
                    return getStringMessage;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: get key");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "set":
                if (s.length == 3 && isNotBlank(s[1]) && isNotBlank(s[2])) {
                    SetStringMessage setStringMessage = new SetStringMessage(s[1], s[2]);
                        if (channel != null)
                            channel.writeAndFlush(setStringMessage);
                    return setStringMessage;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: set key value");
                        if (channel != null)
                            channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "mset":
                if (s.length >= 3 && s.length % 2 == 1) {
                    int size = (s.length - 1) / 2;
                    String[] keys = new String[size];
                    String[] values = new String[size];
                    for (int i = 0; i < size; i++) {
                        if (!isNotBlank(s[i * 2 + 1]) || !isNotBlank(s[(i + 1) * 2])) {
                            ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: set key1 value1 key2 value2...");

                                if (channel != null)
                                    channel.writeAndFlush(responseMessage);
                            return responseMessage;
                        }
                        keys[i] = s[i * 2 + 1];
                        values[i] = s[(i + 1) * 2];
                    }
                    MSetStringMessage message = new MSetStringMessage();
                    message.setKeys(keys);
                    message.setValues(values);

                        if (channel != null)
                            channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: set key1 value1 key2 value2...");
                        if (channel != null)
                            channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "mget":
                if (s.length >= 2) {
                    int size = s.length - 1;
                    String[] keys = new String[size];
                    for (int i = 0; i < size; i++) {
                        keys[i] = s[i + 1];
                    }
                    MGetStringMessage message = new MGetStringMessage();

                    message.setKeys(keys);

                        if (channel != null)
                            channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: set key1 value1 key2 value2");

                        if (channel != null)
                            channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "incr":
                if (s.length == 2 && isNotBlank(s[1])) {
                    IncrStringMessage message = new IncrStringMessage(s[1]);
                        if (channel != null)
                            channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: incr key");
                        if (channel != null)
                            channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "incrby":
                if (s.length == 3 && isNotBlank(s[1]) && isNotBlank(s[2])) {
                    IncrByStringMessage message = new IncrByStringMessage();
                    try {
                        int step = Integer.parseInt(s[2]);
                        message.setKey(s[1]);
                        message.setStep(step);
                    } catch (NumberFormatException e) {
                        ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "can not convert specify step to number");
                        if (channel != null)

                                channel.writeAndFlush(responseMessage);
                        return responseMessage;
                    }
                    if (channel != null)
                            channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: incrby key step");
                    if (channel != null)

                            channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "setnx":
                if (s.length == 3 && isNotBlank(s[1]) && isNotBlank(s[2])) {
                    SetNxStringMessage message = new SetNxStringMessage(s[1], s[2]);
                        if (channel != null)
                            channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: setnx key value");
                    if (channel != null)
                            channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "setex":
                if (s.length == 4 && isNotBlank(s[1]) && isNotBlank(s[2]) && isNotBlank(s[3])) {
                    SetExStringMessage message = new SetExStringMessage();
                    message.setKey(s[1]);
                    message.setValue(s[2]);
                    try {
                        Long l = Long.parseLong(s[3]);
                        message.setExpire(l);
                    } catch (NumberFormatException e) {
                        ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "can not convert specify expire to number");
                        if (channel != null)

                                channel.writeAndFlush(responseMessage);
                        return responseMessage;
                    }
                    if (channel != null)

                            channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: setex key value expire");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "hset":
                if (s.length == 4 && isNotBlank(s[1]) && isNotBlank(s[2]) && isNotBlank(s[3])) {
                    HSetMessage message = new HSetMessage();
                    message.setName(s[1]);
                    message.setKey(s[2]);
                    message.setValue(s[3]);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: hset field key value");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }
            case "hget":
                if (s.length == 3 && isNotBlank(s[1]) && isNotBlank(s[2])) {
                    HGetMessage message = new HGetMessage();
                    message.setName(s[1]);
                    message.setKey(s[2]);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: hget field key");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "hmset":
                if (s.length >= 4 && s.length % 2 == 0) {
                    int size = (s.length) / 2 - 1;
                    String[] keys = new String[size];
                    String[] values = new String[size];
                    for (int i = 0; i < size; i++) {
                        if (!isNotBlank(s[(i + 1) * 2]) || !isNotBlank(s[(i + 1) * 2 + 1])) {
                            ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: hmset field key1 value1 key2 value2...");
                            if (channel != null)
                                channel.writeAndFlush(responseMessage);
                            return responseMessage;
                        }
                        keys[i] = s[(i + 1) * 2];
                        values[i] = s[(i + 1) * 2 + 1];
                    }
                    HMSetMessage message = new HMSetMessage();
                    message.setName(s[1]);
                    message.setKeys(keys);
                    message.setValues(values);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: hmset field key1 value1 key2 value2...");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "hmget":
                if (s.length >= 3) {
                    int size = s.length - 2;
                    String[] keys = new String[size];
                    for (int i = 0; i < size; i++) {
                        if (!isNotBlank(s[i + 2])) {
                            ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: hmget field key1 key2...");
                            if (channel != null)
                                channel.writeAndFlush(responseMessage);
                            return responseMessage;
                        }
                        keys[i] = s[i + 2];
                    }
                    HMGetMessage message = new HMGetMessage();
                    message.setName(s[1]);
                    message.setKeys(keys);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: hmset field key1 key2...");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "hgetall":
                if (s.length == 2 && isNotBlank(s[1])) {
                    HGetAllMessage message = new HGetAllMessage(s[1]);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: hgetall field");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "hkeys":
                if (s.length == 2 && isNotBlank(s[1])) {
                    HKeysMessage message = new HKeysMessage(s[1]);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: hkeys field");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "hvals":
                if (s.length == 2 && isNotBlank(s[1])) {
                    HValuesMessage message = new HValuesMessage(s[1]);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: hvals field");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "hincrby":
                if (s.length == 4 && isNotBlank(s[1]) && isNotBlank(s[2]) && isNotBlank(s[3])) {
                    HIncrByMessage message = new HIncrByMessage();
                    try {
                        message.setName(s[1]);
                        message.setKey(s[2]);

                        int step = Integer.parseInt(s[3]);
                        message.setStep(step);
                    } catch (NumberFormatException e) {
                        ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "can not convert specify step to number");
                        if (channel != null)
                            channel.writeAndFlush(responseMessage);
                        return responseMessage;
                    }
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: hincrby field name step");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "hsetnx":
                if (s.length == 4 && isNotBlank(s[1]) && isNotBlank(s[2]) && isNotBlank(s[3])) {
                    HSetNxMessage message = new HSetNxMessage();
                    message.setName(s[1]);
                    message.setKey(s[2]);
                    message.setValue(s[3]);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: hsetnx field key value");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "sadd":
                if (s.length >= 3 && isNotBlank(s[1])) {
                    int size = s.length - 2;
                    String[] values = new String[size];
                    for (int i = 0; i < size; i++) {
                        values[i] = s[i + 2];
                    }
                    SAddMessage message = new SAddMessage();
                    message.setName(s[1]);
                    message.setValues(values);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: sadd field value1 value2...");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "srem":
                if (s.length >= 3 && isNotBlank(s[1])) {
                    int size = s.length - 2;
                    String[] values = new String[size];
                    for (int i = 0; i < size; i++) {
                        values[i] = s[i + 2];
                    }
                    SRemMessage message = new SRemMessage();
                    message.setName(s[1]);
                    message.setValues(values);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: srem field value1 value2...");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "scard":
                if (s.length == 2 && isNotBlank(s[1])) {
                    SCardMessage message = new SCardMessage(s[1]);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: scard key");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "smembers":
                if (s.length == 2 && isNotBlank(s[1])) {
                    SMembersMessage message = new SMembersMessage(s[1]);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: scard key");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "sismember":
                if (s.length == 3 && isNotBlank(s[1]) && isNotBlank(s[2])) {
                    SIsMemberMessage message = new SIsMemberMessage(s[1], s[2]);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: sismember key value");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "sinter":
                if (s.length == 3 && isNotBlank(s[1]) && isNotBlank(s[2])) {
                    SInterMessage message = new SInterMessage(s[1], s[2]);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: sinter key1 key2");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "sdiff":
                if (s.length == 3 && isNotBlank(s[1]) && isNotBlank(s[2])) {
                    SDiffMessage message = new SDiffMessage(s[1], s[2]);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: sdiff key1 key2");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "sunion":
                if (s.length == 3 && isNotBlank(s[1]) && isNotBlank(s[2])) {
                    SUnionMessage message = new SUnionMessage(s[1], s[2]);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: sunion key1 key2");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "lpush":
                if (s.length >= 3 && isNotBlank(s[1])) {
                    int size = s.length - 2;
                    String[] values = new String[size];
                    for (int i = 0; i < size; i++) {
                        values[i] = s[i + 2];
                    }
                    LPushMessage message = new LPushMessage();
                    message.setName(s[1]);
                    message.setValues(values);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: lpush key element");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "lpop":
                if (s.length == 2 && isNotBlank(s[1])) {
                    LPopMessage message = new LPopMessage(s[1]);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: lpop key");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "rpush":
                if (s.length >= 3 && isNotBlank(s[1])) {
                    int size = s.length - 2;
                    String[] values = new String[size];
                    for (int i = 0; i < size; i++) {
                        values[i] = s[i + 2];
                    }
                    RPushMessage message = new RPushMessage();
                    message.setName(s[1]);
                    message.setValues(values);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: rpush key element");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "rpop":
                if (s.length == 2 && isNotBlank(s[1])) {
                    RPopMessage message = new RPopMessage(s[1]);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: rpop key");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "blpop":
                if (s.length == 2 && isNotBlank(s[1])) {
                    BLpopMessage message = new BLpopMessage(s[1]);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: blpop key");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "brpop":
                if (s.length == 2 && isNotBlank(s[1])) {
                    BRpopMessage message = new BRpopMessage(s[1]);
                    if (channel != null)
                        channel.writeAndFlush(message);
                    return message;
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: brpop key");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            case "lrange":
                if (s.length == 4 && isNotBlank(s[1])) {
                    try {
                        int start = Integer.parseInt(s[2]);
                        int end = Integer.parseInt(s[3]);
                        LRangeMessage message = new LRangeMessage(s[1], start, end);
                        if (channel != null)
                            channel.writeAndFlush(message);
                        return message;
                    } catch (NumberFormatException e) {
                        ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "can not cast string to integer");
                        if (channel != null)
                            channel.writeAndFlush(responseMessage);
                        return responseMessage;
                    }
                } else {
                    ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "correct format: brpop key");
                    if (channel != null)
                        channel.writeAndFlush(responseMessage);
                    return responseMessage;
                }

            default:

                ResponseMessage responseMessage = new ResponseMessage(ResultCode.ERROR_CODE, null, "use help command to correct");
                if (channel != null)
                    channel.writeAndFlush(responseMessage);
                return responseMessage;

        }
    }
}
