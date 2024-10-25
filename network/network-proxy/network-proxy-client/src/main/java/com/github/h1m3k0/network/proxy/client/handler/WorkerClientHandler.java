package com.github.h1m3k0.network.proxy.client.handler;

import com.github.h1m3k0.network.proxy.client.AttributeKeys;
import com.github.h1m3k0.network.proxy.common.DataMessage;
import com.github.h1m3k0.network.proxy.common.DisconnectMessage;
import com.github.h1m3k0.network.proxy.common.ProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class WorkerClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel workerChannel = ctx.channel();
        workerChannel.attr(AttributeKeys.bossChannel).get().attr(AttributeKeys.workerChannelMap).get()
                .put(workerChannel.attr(AttributeKeys.workerKey).get(), workerChannel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel workerChannel = ctx.channel();
        Channel bossChannel = workerChannel.attr(AttributeKeys.bossChannel).get();
        bossChannel.writeAndFlush(new ProxyPacket(new DisconnectMessage(workerChannel.attr(AttributeKeys.workerKey).get())).toBuf());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        Channel workerChannel = ctx.channel();
        String key = workerChannel.attr(AttributeKeys.workerKey).get();
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        workerChannel.attr(AttributeKeys.bossChannel).get().writeAndFlush(new ProxyPacket(new DataMessage(key, bytes)).toBuf());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace(System.err);
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("evt:" + evt);
        super.userEventTriggered(ctx, evt);
    }
}
