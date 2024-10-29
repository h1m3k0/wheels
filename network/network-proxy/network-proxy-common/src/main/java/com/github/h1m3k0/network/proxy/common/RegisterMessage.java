package com.github.h1m3k0.network.proxy.common;

import com.github.h1m3k0.common.bytes.Byte4Number;
import com.github.h1m3k0.common.bytes.ByteArray;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class RegisterMessage extends ProxyMessage {
    private final int port;

    public RegisterMessage(ByteBuf buf) {
        super(ProxyType.Register);
        Byte4Number byte4Number = new Byte4Number(ByteArray.byteBuf(buf));
        this.port = (int) byte4Number.toInt();
    }

    public RegisterMessage(int port) {
        super(ProxyType.Register);
        this.port = port;
    }

    @Override
    protected byte[][] toByteArray() {
        return new byte[][]{new Byte4Number(port).toBytes()};
    }
}
