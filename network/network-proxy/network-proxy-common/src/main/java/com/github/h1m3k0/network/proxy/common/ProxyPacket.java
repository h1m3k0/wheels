package com.github.h1m3k0.network.proxy.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true, fluent = true)
public class ProxyPacket {
    private ProxyPacketType type;
    private ProxyMessage message;

    public ProxyPacket(ProxyMessage message) {
        this.message = message;
        if (message instanceof DataMessage) {
            this.type = ProxyPacketType.Data;
        } else if (message instanceof RegisterMessage) {
            this.type = ProxyPacketType.Register;
        } else if (message instanceof UnRegisterMessage) {
            this.type = ProxyPacketType.UnRegister;
        } else if (message instanceof ConnectMessage) {
            this.type = ProxyPacketType.Connect;
        } else if (message instanceof DisconnectMessage) {
            this.type = ProxyPacketType.Disconnect;
        }
    }

    public ProxyPacket(ProxyPacketType type, byte[] bytes) {
        this.type = type;
        switch (type) {
            case Data:
                message = new DataMessage(bytes);
                break;
            case Register:
                message = new RegisterMessage(bytes);
                break;
            case UnRegister:
                message = new UnRegisterMessage(bytes);
                break;
            case Connect:
                message = new ConnectMessage(bytes);
                break;
            case Disconnect:
                message = new DisconnectMessage(bytes);
                break;
            case Undefined:
                throw new IllegalArgumentException();
        }
    }

    public ByteBuf toBuf() {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeInt(0);
        buf.writeInt(this.type.code());
        buf.writeBytes(message.toBytes());
        buf.setInt(0, buf.readableBytes() - 4);
        return buf;
    }
}
