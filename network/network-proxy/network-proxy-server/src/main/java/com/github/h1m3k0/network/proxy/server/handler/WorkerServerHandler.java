package com.github.h1m3k0.network.proxy.server.handler;

import com.github.h1m3k0.network.proxy.common.ConnectMessage;
import com.github.h1m3k0.network.proxy.common.DataMessage;
import com.github.h1m3k0.network.proxy.common.DisconnectMessage;
import com.github.h1m3k0.network.proxy.common.MessageKey;
import com.github.h1m3k0.network.proxy.server.AttributeKeys;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@ChannelHandler.Sharable
public class WorkerServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final Map<Integer, Channel> PortBossChannelMap;

    public WorkerServerHandler(Map<Integer, Channel> PortBossChannelMap) {
        this.PortBossChannelMap = PortBossChannelMap;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel workerChannel = ctx.channel();
        MessageKey key = new MessageKey();
        workerChannel.attr(AttributeKeys.workerKey).set(key);
        InetSocketAddress address = (InetSocketAddress) workerChannel.localAddress();
        Channel bossChannel = PortBossChannelMap.get(address.getPort());
        workerChannel.attr(AttributeKeys.bossChannel).set(bossChannel);
        workerChannel.attr(AttributeKeys.connected).set(false);
        workerChannel.attr(AttributeKeys.initData).set(new ConcurrentLinkedQueue<>());
        bossChannel.attr(AttributeKeys.workerChannelMap).get().put(key, workerChannel);
        bossChannel.pipeline().writeAndFlush(new ConnectMessage(key));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel workerChannel = ctx.channel();
        MessageKey key = workerChannel.attr(AttributeKeys.workerKey).get();
        Channel bossChannel = workerChannel.attr(AttributeKeys.bossChannel).get();
        bossChannel.pipeline().writeAndFlush(new DisconnectMessage(key));
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        Channel workerChannel = ctx.channel();
        Channel bossChannel = workerChannel.attr(AttributeKeys.bossChannel).get();
        byte[] dataBytes = new byte[buf.readableBytes()];
        buf.readBytes(dataBytes);
        if (workerChannel.attr(AttributeKeys.connected).get()) {
            MessageKey key = workerChannel.attr(AttributeKeys.workerKey).get();
            bossChannel.pipeline().writeAndFlush(new DataMessage(key, dataBytes));
        } else {
            workerChannel.attr(AttributeKeys.initData).get().add(dataBytes);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace(System.err);
        ctx.close();
    }
}
