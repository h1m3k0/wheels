package com.github.h1m3k0.network.proxy.common;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
public abstract class ProxyMessage {
    protected final ProxyType type;

    public ProxyMessage(final ProxyType type) {
        this.type = type;
    }
}
