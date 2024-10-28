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
        int length = buf.readInt();
        int type = buf.readInt();
        byte[] bytes = new byte[length - 4];
        buf.readBytes(bytes);
        switch (ProxyType.get(type)) {
            case Data:
                out.add(new DataMessage(bytes));
                break;
            case Register:
                out.add(new RegisterMessage(bytes));
                break;
            case Connect:
                out.add(new ConnectMessage(bytes));
                break;
            case Disconnect:
                out.add(new DisconnectMessage(bytes));
                break;
        }
    }
}
