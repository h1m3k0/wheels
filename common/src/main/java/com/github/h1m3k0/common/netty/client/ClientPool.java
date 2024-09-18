package com.github.h1m3k0.common.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public abstract class ClientPool {
    protected final Bootstrap bootstrap = new Bootstrap();
    protected final EventLoopGroup group = new NioEventLoopGroup();

    public ClientPool() {
        bootstrap.group(group).channel(NioSocketChannel.class);
    }

    public void shutdown() {
        group.shutdownGracefully();
    }
}
