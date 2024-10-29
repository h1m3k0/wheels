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
    private MessageKey key;

    public DisconnectMessage(ByteBuf buf) {
        super(ProxyType.Disconnect);
        this.key = new MessageKey(buf);
    }

    public DisconnectMessage(MessageKey key) {
        super(ProxyType.Disconnect);
        this.key = key;
    }

    @Override
    protected byte[][] toByteArray() {
        return new byte[][]{this.key.bytes()};
    }
}
