package com.cjl.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class MessageProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {

    //最大长度，长度偏移，长度占用字节，长度调整，剥离字节数
    public MessageProtocolFrameDecoder() {
        this(1024, 16, 4, 0, 0);
    }

    public MessageProtocolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
}
