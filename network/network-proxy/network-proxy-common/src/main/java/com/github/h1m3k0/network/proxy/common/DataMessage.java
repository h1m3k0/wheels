package com.github.h1m3k0.network.proxy.common;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class DataMessage extends ProxyMessage {
    private MessageKey key;
    private byte[] data;

    public DataMessage(ByteBuf buf) {
        super(ProxyType.Data);
        this.key = new MessageKey(buf);
        buf.readBytes(this.data = new byte[buf.readableBytes()]);
    }

    public DataMessage(MessageKey key, byte[] bytes) {
        super(ProxyType.Data);
        this.key = key;
        this.data = bytes;
    }

    @Override
    protected byte[][] toByteArray() {
        return new byte[][]{
                this.key.bytes(),
                this.data
        };
    }

}
