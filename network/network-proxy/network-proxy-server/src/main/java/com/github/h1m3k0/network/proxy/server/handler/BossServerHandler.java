package com.github.h1m3k0.network.proxy.server.handler;

import com.github.h1m3k0.network.proxy.common.*;
import com.github.h1m3k0.network.proxy.server.AttributeKeys;
import com.github.h1m3k0.network.proxy.server.WorkerServer;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Queue;

@ChannelHandler.Sharable
public class BossServerHandler extends SimpleChannelInboundHandler<ProxyMessage> {
    private final WorkerServer workerServer;

    public BossServerHandler(WorkerServer workerServer) {
        this.workerServer = workerServer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().attr(AttributeKeys.workerChannelMap).set(new HashMap<>());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel bossChannel = ctx.channel();
        bossChannel.attr(AttributeKeys.linkChannel).get().close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProxyMessage proxyMessage) throws Exception {
        Channel bossChannel = ctx.channel();
        switch (proxyMessage.type()) {
            case Data: {
                DataMessage message = (DataMessage) proxyMessage;
                Channel workerChannel = bossChannel.attr(AttributeKeys.workerChannelMap).get().get(message.key());
                workerChannel.writeAndFlush(Unpooled.wrappedBuffer(message.data()));
                break;
            }
            case Register: {
                RegisterMessage message = (RegisterMessage) proxyMessage;
                workerServer.bind(message.port(), bossChannel);
                break;
            }
            case Connect: {
                ConnectMessage message = (ConnectMessage) proxyMessage;
                Channel workerChannel = bossChannel.attr(AttributeKeys.workerChannelMap).get().get(message.key());
                Queue<byte[]> initData = workerChannel.attr(AttributeKeys.initData).get();
                byte[] bytes;
                while ((bytes = initData.poll()) != null) {
                    bossChannel.writeAndFlush(new DataMessage(message.key(), bytes).toBuf());
                }
                workerChannel.attr(AttributeKeys.connected).set(true);
                break;
            }
            case Disconnect: {
                DisconnectMessage message = (DisconnectMessage) proxyMessage;
                Channel workerChannel = bossChannel.attr(AttributeKeys.workerChannelMap).get().get(message.key());
                workerChannel.close();
                break;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace(System.err);
        ctx.close();
    }
}
