package com.github.h1m3k0.common.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;

import java.util.function.Consumer;

public abstract class Client<config extends Config<config, client, pool>, client extends Client<config, client, pool>, pool extends ClientPool<config, client, pool>> implements AutoCloseable {
    protected final pool pool;
    protected final String host;
    protected final int port;
    public Channel channel;
    protected boolean enable;

    public Client(pool pool, String host, int port) {
        this.pool = pool;
        this.host = host;
        this.port = port;
    }

    public ChannelFuture connect() {
        enable = true;
        return this.reconnect();
    }

    private synchronized ChannelFuture reconnect() {
        ChannelFuture channelFuture = pool.bootstrap.connect(host, port);
        channel = channelFuture.channel();
        channel.closeFuture().addListener(listener -> {
            if (pool.group.isShuttingDown() || pool.group.isShutdown() || pool.group.isTerminated()) {
                enable = false;
            } else if (enable) {
                this.reconnect();
            }
        });
        return channelFuture;
    }

    @Override
    public void close() {
        close(future -> {
        });
    }

    public void close(Consumer<ChannelFuture> channelFuture) {
        enable = false;
        channelFuture.accept(channel.close());
    }
}
