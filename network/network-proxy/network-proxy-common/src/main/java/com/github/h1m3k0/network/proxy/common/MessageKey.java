package com.github.h1m3k0.network.proxy.common;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.experimental.Accessors;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Data
@Accessors(chain = true, fluent = true)
public class MessageKey {
    public static final int LENGTH = UUID.randomUUID().toString().replaceAll("-", "").length();
    private final byte[] bytes;

    public MessageKey() {
        this.bytes = UUID.randomUUID().toString().replaceAll("-", "").getBytes(StandardCharsets.UTF_8);
    }

    public MessageKey(String key) {
        this.bytes = key.getBytes(StandardCharsets.UTF_8);
    }

    public MessageKey(byte[] bytes) {
        this.bytes = bytes;
    }

    public MessageKey(ByteBuf buf) {
        buf.readBytes(this.bytes = new byte[LENGTH]);
    }
}
