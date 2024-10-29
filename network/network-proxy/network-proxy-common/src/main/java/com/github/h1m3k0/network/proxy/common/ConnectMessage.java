package com.github.h1m3k0.network.proxy.common;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class ConnectMessage extends ProxyMessage {
    private MessageKey key;

    public ConnectMessage(ByteBuf buf) {
        super(ProxyType.Connect);
        this.key = new MessageKey(buf);
    }

    public ConnectMessage(MessageKey key) {
        super(ProxyType.Connect);
        this.key = key;
    }

    @Override
    protected byte[][] toByteArray() {
        return new byte[][]{this.key.bytes()};
    }
}
