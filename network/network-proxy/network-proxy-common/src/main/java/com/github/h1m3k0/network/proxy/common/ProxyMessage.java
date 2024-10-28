package com.github.h1m3k0.network.proxy.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
public abstract class ProxyMessage {
    protected final ProxyType type;

    public ProxyMessage(final ProxyType type) {
        this.type = type;
    }

    public abstract byte[] toBytes();
    public ByteBuf toBuf() {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeInt(0);
        buf.writeInt(this.type.code());
        buf.writeBytes(toBytes());
        buf.setInt(0, buf.readableBytes() - 4);
        return buf;
    }
}
