package com.github.h1m3k0.network.proxy.client;

import com.github.h1m3k0.network.proxy.client.handler.WorkerClientHandler;
import com.github.h1m3k0.network.proxy.common.DisconnectMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Queue;
import java.util.concurrent.ExecutionException;

public class WorkerClient {
    private final Bootstrap bootstrap;

    public WorkerClient() {
        WorkerClientHandler handler = new WorkerClientHandler();
        bootstrap = new Bootstrap().channel(NioSocketChannel.class)
                .group(new NioEventLoopGroup())
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(handler);
                    }
                });
    }

    public void connect(String host, int port, Channel bossChannel, String key) throws ExecutionException, InterruptedException {
        ChannelFuture channelFuture = bootstrap.connect(host, port);
        Channel workerChannel = channelFuture.channel();
        workerChannel.attr(AttributeKeys.bossChannel).set(bossChannel);
        workerChannel.attr(AttributeKeys.workerKey).set(key);
        bossChannel.attr(AttributeKeys.workerChannelMap).get().put(key, workerChannel);
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                Queue<ByteBuf> queue = workerChannel.attr(AttributeKeys.cacheData).get();
                if (queue != null) {
                    ByteBuf buf;
                    while((buf = queue.poll()) != null) {
                        workerChannel.writeAndFlush(buf);
                    }
                }
            } else {
                bossChannel.writeAndFlush(new DisconnectMessage(key).toBuf());
            }
        });
    }
}
