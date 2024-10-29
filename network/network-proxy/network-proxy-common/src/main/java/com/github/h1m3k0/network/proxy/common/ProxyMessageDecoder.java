package com.github.h1m3k0.network.proxy.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

@ChannelHandler.Sharable
public class ProxyMessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        buf.readInt();
        int type = buf.readInt();
        switch (ProxyType.get(type)) {
            case Data:
                out.add(new DataMessage(buf));
                break;
            case Register:
                out.add(new RegisterMessage(buf));
                break;
            case UnRegister:
                out.add(new UnRegisterMessage(buf));
                break;
            case Connect:
                out.add(new ConnectMessage(buf));
                break;
            case Disconnect:
                out.add(new DisconnectMessage(buf));
                break;
        }
    }
}
