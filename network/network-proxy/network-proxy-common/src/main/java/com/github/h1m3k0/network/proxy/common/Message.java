package com.github.h1m3k0.network.proxy.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Message {
    private int length = 0;
    private int type = 0;
    private String key;
    private String message = "";

    public byte[] toBytes() {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeInt(length);
        buf.writeInt(type);
        buf.writeBytes(key.getBytes(StandardCharsets.UTF_8));
        buf.writeBytes(message.getBytes(StandardCharsets.UTF_8));
        buf.setInt(0, buf.readableBytes() - 4);
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        return bytes;
    }

    public Message(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);
        this.length = buf.readInt();
        this.type = buf.readInt();
        byte[] keyBytes = new byte[UUID.randomUUID().toString().length()];
        buf.readBytes(keyBytes);
        this.key = new String(keyBytes, StandardCharsets.UTF_8);
        byte[] messageBytes = new byte[this.length - 4 - keyBytes.length];
        buf.readBytes(messageBytes);
        this.message = new String(messageBytes, StandardCharsets.UTF_8);
    }
}
