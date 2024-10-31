package com.github.h1m3k0.network.proxy.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class DataMessage extends ProxyMessage {
    private MessageKey key;
    private byte[] data;

    public DataMessage(MessageKey key, byte[] bytes) {
        super(ProxyType.Data);
        this.key = key;
        this.data = bytes;
    }
}
