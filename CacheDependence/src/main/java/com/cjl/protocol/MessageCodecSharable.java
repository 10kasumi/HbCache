package com.cjl.protocol;

import com.cjl.message.Message;
import com.cjl.utils.ServerPropertiesUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * 必须和 LengthFieldBasedFrameDecoder 一起使用，确保接到的 ByteBuf 消息是完整的
 */

@Slf4j
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> outList) throws Exception {
        ByteBuf out = ctx.alloc().buffer();
        // 魔数
        out.writeBytes(new byte[]{1, 2, 3, 4});
        //字节版本
        out.writeByte(1);
        // 序列化方式
        out.writeByte(ServerPropertiesUtils.getSerializerAlgorithm().ordinal());
        // 4字节指令
        out.writeInt(msg.getMessageType());
        out.writeInt(msg.getSequenceId());
        out.writeByte(0xff);
        out.writeByte(0xff);
        byte[] bytes = ServerPropertiesUtils.getSerializerAlgorithm().serialize(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
        outList.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> outList) throws Exception {
        int magicNum = msg.readInt();
        byte version = msg.readByte();
        byte serializerAlgorithm = msg.readByte();
        int messageType = msg.readInt();
        int sequenceId = msg.readInt();
        msg.readByte();
        msg.readByte();
        int length = msg.readInt();
        byte[] bytes = new byte[length];
        msg.readBytes(bytes, 0, length);

        Serializer.Algorithm algorithm = Serializer.Algorithm.values()[serializerAlgorithm];
        Class<? extends Message> messageClass = Message.getMessageClass(messageType);
        Message message = algorithm.deserialize(messageClass, bytes);
        outList.add(message);
    }
}
