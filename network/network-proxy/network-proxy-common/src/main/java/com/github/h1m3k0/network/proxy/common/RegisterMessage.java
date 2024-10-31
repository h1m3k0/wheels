package com.github.h1m3k0.network.proxy.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class RegisterMessage extends ProxyMessage {
    private final int port;

    public RegisterMessage(int port) {
        super(ProxyType.Register);
        this.port = port;
    }
}
