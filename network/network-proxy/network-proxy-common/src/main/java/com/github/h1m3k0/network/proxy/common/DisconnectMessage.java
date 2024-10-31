package com.github.h1m3k0.network.proxy.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class DisconnectMessage extends ProxyMessage {
    private MessageKey key;

    public DisconnectMessage(MessageKey key) {
        super(ProxyType.Disconnect);
        this.key = key;
    }
}
