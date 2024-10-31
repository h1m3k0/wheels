package com.github.h1m3k0.network.proxy.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class ConnectMessage extends ProxyMessage {
    private MessageKey key;

    public ConnectMessage(MessageKey key) {
        super(ProxyType.Connect);
        this.key = key;
    }
}
