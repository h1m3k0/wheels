package com.github.h1m3k0.network.proxy.client.handler;

import com.github.h1m3k0.network.proxy.client.AttributeKeys;
import com.github.h1m3k0.network.proxy.client.WorkerClient;
import com.github.h1m3k0.network.proxy.common.*;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

@ChannelHandler.Sharable
public class BossClientHandler extends SimpleChannelInboundHandler<ProxyPacket> {
    private final WorkerClient workerClient;

    public BossClientHandler(WorkerClient workerClient) {
        this.workerClient = workerClient;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(new ProxyPacket(
                new RegisterMessage(ctx.channel().attr(AttributeKeys.targetWorkerPort).get()
                )).toBuf());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProxyPacket packet) throws Exception {
        Channel bossChannel = ctx.channel();
        switch (packet.type()) {
            case Data: {
                DataMessage message = (DataMessage) packet.message();
                Channel workerChannel = bossChannel.attr(AttributeKeys.workerChannelMap).get().get(message.key());
                workerChannel.writeAndFlush(Unpooled.wrappedBuffer(message.bytes()));
                break;
            }
            case Connect: {
                ConnectMessage message = (ConnectMessage) packet.message();
                bossChannel.attr(AttributeKeys.workerChannelMap).set(new HashMap<>());
                workerClient.connect(
                        bossChannel.attr(AttributeKeys.thisWorkerHost).get(),
                        bossChannel.attr(AttributeKeys.thisWorkerPort).get(),
                        bossChannel, message.key());
                break;
            }
            case Disconnect: {
                DisconnectMessage message = (DisconnectMessage) packet.message();
                Map<String, Channel> workerChannelMap = bossChannel.attr(AttributeKeys.workerChannelMap).get();
                Channel workerChannel = workerChannelMap.get(message.key());
                if (workerChannel != null) {
                    workerChannel.close();
                }
                break;
            }
        }
    }
}
