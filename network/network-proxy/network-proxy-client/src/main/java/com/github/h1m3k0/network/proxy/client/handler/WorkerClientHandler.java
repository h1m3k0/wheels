package com.github.h1m3k0.network.proxy.client.handler;

import com.github.h1m3k0.network.proxy.client.AttributeKeys;
import com.github.h1m3k0.network.proxy.common.DataMessage;
import com.github.h1m3k0.network.proxy.common.DisconnectMessage;
import com.github.h1m3k0.network.proxy.common.MessageKey;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class WorkerClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        Channel workerChannel = ctx.channel();
        MessageKey key = workerChannel.attr(AttributeKeys.workerKey).get();
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        workerChannel.attr(AttributeKeys.bossChannel).get().writeAndFlush(new DataMessage(key, bytes).toBuf());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace(System.err);
        ctx.close();
    }
}
