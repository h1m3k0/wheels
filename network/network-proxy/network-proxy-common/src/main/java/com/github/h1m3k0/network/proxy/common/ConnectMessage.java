package com.github.h1m3k0.network.proxy.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class ConnectMessage extends ProxyMessage {
    private String key;

    public ConnectMessage(byte[] bytes) {
        super(ProxyType.Connect);
        this.key = new String(bytes, StandardCharsets.UTF_8);
    }

    public ConnectMessage(String key) {
        super(ProxyType.Connect);
        this.key = key;
    }

    @Override
    public byte[] toBytes() {
        return this.key.getBytes(StandardCharsets.UTF_8);
    }
}
