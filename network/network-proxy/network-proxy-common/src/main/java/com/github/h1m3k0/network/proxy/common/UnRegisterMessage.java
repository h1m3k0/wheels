package com.github.h1m3k0.network.proxy.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class UnRegisterMessage extends ProxyMessage {
    private int port;

    public UnRegisterMessage(int port) {
        super(ProxyType.UnRegister);
        this.port = port;
    }
}
