package com.github.h1m3k0.network.proxy.common;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class DisconnectMessage extends ProxyMessage {
    private String key;

    public DisconnectMessage(ByteBuf buf) {
        super(ProxyType.Disconnect);
        byte[] bytes = new byte[36];
        buf.readBytes(bytes);
        this.key = new String(bytes, StandardCharsets.UTF_8);
    }

    public DisconnectMessage(String key) {
        super(ProxyType.Disconnect);
        this.key = key;
    }

    @Override
    protected byte[][] toByteArray() {
        return new byte[][]{this.key.getBytes(StandardCharsets.UTF_8)};
    }
}
