package com.github.h1m3k0.network.proxy.common;

import lombok.Data;

@Data
public class Message {
    private int length;
    private int type;
    private String key;
    private String message;

    public byte[] toBytes() {
        return new byte[0];
    }

    public Message(byte[] bytes) {

    }
}
