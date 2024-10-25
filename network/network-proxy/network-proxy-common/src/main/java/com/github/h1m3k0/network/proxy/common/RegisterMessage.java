package com.github.h1m3k0.network.proxy.common;

import com.github.h1m3k0.common.bytes.Byte4Number;
import com.github.h1m3k0.common.bytes.ByteArray;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true, fluent = true)
public class RegisterMessage implements ProxyMessage {
    private final int port;

    public RegisterMessage(byte[] bytes) {
        Byte4Number byte4Number = new Byte4Number(ByteArray.array(bytes));
        this.port = (int) byte4Number.toInt();
    }

    public RegisterMessage(int port) {
        this.port = port;
    }

    @Override
    public byte[] toBytes() {
        return new Byte4Number(port).toBytes();
    }
}
