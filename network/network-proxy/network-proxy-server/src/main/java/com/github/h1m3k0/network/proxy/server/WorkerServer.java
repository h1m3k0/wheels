package com.github.h1m3k0.network.proxy.server;

import com.github.h1m3k0.network.proxy.common.UnRegisterMessage;
import com.github.h1m3k0.network.proxy.server.handler.WorkerServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WorkerServer {
    private final Map<Integer, Channel> PortBossChannelMap = new ConcurrentHashMap<>();
    private final ServerBootstrap bootstrap;

    public WorkerServer() {
        WorkerServerHandler handler = new WorkerServerHandler(PortBossChannelMap);
        bootstrap = new ServerBootstrap()
                .channel(NioServerSocketChannel.class)
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(handler);
                    }
                });
    }

    public void bind(int port, Channel bossChannel) {
        ChannelFuture channelFuture = bootstrap.bind(port);
        Channel linkChannel = channelFuture.channel();
        PortBossChannelMap.put(port, bossChannel);
        bossChannel.attr(AttributeKeys.linkChannel).set(linkChannel);
        linkChannel.closeFuture().addListener(listener -> {
            if (PortBossChannelMap.get(port) == bossChannel) {
                PortBossChannelMap.remove(port);
            }
            bossChannel.writeAndFlush(new UnRegisterMessage(port));
        });
    }
}
