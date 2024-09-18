package com.github.h1m3k0.modbus.client.handler;

import com.github.h1m3k0.modbus.client.AttributeKeys;
import com.github.h1m3k0.modbus.core.ModbusMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.CountDownLatch;

@ChannelHandler.Sharable
public abstract class MessageClientHandler<T extends ModbusMessage<?, ?, ?, ?, ?>> extends SimpleChannelInboundHandler<T> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
        ctx.channel().attr(AttributeKeys.Message(msg.msgCount())).set(msg);
        CountDownLatch latch = ctx.channel().attr(AttributeKeys.Lock(msg.msgCount())).get();
        if (latch != null) {
            latch.countDown();
        }
    }
}
