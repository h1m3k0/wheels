package com.github.h1m3k0.network.proxy.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

@ChannelHandler.Sharable
public class ProxyMessageCodec extends MessageToMessageCodec<ByteBuf, ProxyMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ProxyMessage msg, List<Object> out) throws Exception {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeInt(0);
        buf.writeInt(msg.type().code());
        switch (msg.type()) {
            case Register: {
                RegisterMessage message = (RegisterMessage) msg;
                buf.writeInt(message.port());
            }
            break;
            case UnRegister: {
                UnRegisterMessage message = (UnRegisterMessage) msg;
                buf.writeInt(message.port());
            }
            break;
            case Connect: {
                ConnectMessage message = (ConnectMessage) msg;
                buf.writeBytes(message.key().bytes());
            }
            break;
            case Disconnect: {
                DisconnectMessage message = (DisconnectMessage) msg;
                buf.writeBytes(message.key().bytes());
            }
            break;
            case Data: {
                DataMessage message = (DataMessage) msg;
                buf.writeBytes(message.key().bytes());
                buf.writeBytes(message.data());
            }
            break;
        }
        buf.setInt(0, buf.readableBytes() - 4);
        out.add(buf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        buf.readInt();
        int type = buf.readInt();
        switch (ProxyType.get(type)) {
            case Data: {
                MessageKey key = new MessageKey(buf);
                byte[] data = new byte[buf.readableBytes()];
                buf.readBytes(data);
                out.add(new DataMessage(key, data));
            }
            break;
            case Register: {
                out.add(new RegisterMessage(buf.readInt()));
            }
            break;
            case UnRegister: {
                out.add(new UnRegisterMessage(buf.readInt()));
            }
            break;
            case Connect: {
                out.add(new ConnectMessage(new MessageKey(buf)));
            }
            break;
            case Disconnect:{
                out.add(new DisconnectMessage(new MessageKey(buf)));
            }
            break;
        }
    }
}
