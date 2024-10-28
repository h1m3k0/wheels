package com.github.h1m3k0.network.proxy.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class DataMessage extends ProxyMessage {
    private String key;
    private byte[] bytes;

    public DataMessage(byte[] bytes) {
        super(ProxyType.Data);
        byte[] keyBytes = new byte[UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8).length];
        System.arraycopy(bytes, 0, keyBytes, 0, keyBytes.length);
        this.key = new String(keyBytes, StandardCharsets.UTF_8);
        this.bytes = new byte[bytes.length - keyBytes.length];
        System.arraycopy(bytes, keyBytes.length, this.bytes, 0, this.bytes.length);
    }

    public DataMessage(String key, byte[] bytes) {
        super(ProxyType.Data);
        this.key = key;
        this.bytes = bytes;
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[this.bytes.length + key.getBytes(StandardCharsets.UTF_8).length];
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(keyBytes, 0, bytes, 0, keyBytes.length);
        System.arraycopy(this.bytes, 0, bytes, keyBytes.length, this.bytes.length);
        return bytes;
    }
}
