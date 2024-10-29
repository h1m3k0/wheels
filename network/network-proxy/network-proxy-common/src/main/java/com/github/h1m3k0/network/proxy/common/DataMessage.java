package com.github.h1m3k0.network.proxy.common;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class DataMessage extends ProxyMessage {
    private String key;
    private byte[] bytes;

    public DataMessage(ByteBuf buf) {
        super(ProxyType.Data);
        byte[] keyBytes = new byte[36];
        buf.readBytes(keyBytes);
        this.key = new String(keyBytes, StandardCharsets.UTF_8);
        this.bytes = new byte[buf.readableBytes()];
        buf.readBytes(this.bytes);
    }

    public DataMessage(String key, byte[] bytes) {
        super(ProxyType.Data);
        this.key = key;
        this.bytes = bytes;
    }

    @Override
    protected byte[][] toByteArray() {
        return new byte[][]{
                this.key.getBytes(StandardCharsets.UTF_8),
                this.bytes
        };
    }

}
