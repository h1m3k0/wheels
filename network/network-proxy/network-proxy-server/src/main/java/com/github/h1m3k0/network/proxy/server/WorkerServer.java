package com.github.h1m3k0.network.proxy.server;

import com.github.h1m3k0.network.proxy.server.handler.WorkerServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class WorkerServer {
    private final Map<Integer, Channel> PortBossChannelMap = new HashMap<>();
    private final ServerBootstrap bootstrap;

    public WorkerServer() {
        bootstrap = new ServerBootstrap()
                .channel(NioServerSocketChannel.class)
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new WorkerServerHandler(PortBossChannelMap));
                    }
                });
    }

    public void bind(int port, Channel bossChannel) throws ExecutionException, InterruptedException {
        ChannelFuture channelFuture = bootstrap.bind(port);
        Channel linkChannel = channelFuture.channel();
        PortBossChannelMap.put(port, bossChannel);
        bossChannel.attr(AttributeKeys.linkChannel).set(linkChannel);
        channelFuture.get();
    }
}
