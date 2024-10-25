package com.github.h1m3k0.network.proxy.common;

public class UnRegisterMessage implements ProxyMessage {
    public UnRegisterMessage(byte[] bytes) {

    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }
}
