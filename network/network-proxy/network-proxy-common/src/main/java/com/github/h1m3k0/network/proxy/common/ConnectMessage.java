package com.github.h1m3k0.network.proxy.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.nio.charset.StandardCharsets;

@Data
@Accessors(chain = true, fluent = true)
public class ConnectMessage implements ProxyMessage {
    private String key;

    public ConnectMessage(byte[] bytes) {
        this.key = new String(bytes, StandardCharsets.UTF_8);
    }

    public ConnectMessage(String key) {
        this.key = key;
    }

    @Override
    public byte[] toBytes() {
        return this.key.getBytes(StandardCharsets.UTF_8);
    }
}
