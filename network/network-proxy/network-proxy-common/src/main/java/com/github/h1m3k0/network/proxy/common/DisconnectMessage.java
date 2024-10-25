package com.github.h1m3k0.network.proxy.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.nio.charset.StandardCharsets;

@Data
@Accessors(chain = true, fluent = true)
public class DisconnectMessage implements ProxyMessage {
    private String key;

    public DisconnectMessage(byte[] bytes) {
        this.key = new String(bytes, StandardCharsets.UTF_8);
    }

    public DisconnectMessage(String key) {
        this.key = key;
    }

    @Override
    public byte[] toBytes() {
        return this.key.getBytes(StandardCharsets.UTF_8);
    }
}
