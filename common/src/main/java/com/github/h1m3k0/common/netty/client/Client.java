package com.github.h1m3k0.common.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class Client {
    protected final ClientPool pool;
    protected final String host;
    protected final int port;
    public Channel channel;
    protected ExecutorService executorService = Executors.newSingleThreadExecutor();

    public Client(ClientPool pool, String host, int port) {
        this.pool = pool;
        this.host = host;
        this.port = port;
    }

    public ChannelFuture connect() {
        if (executorService.isShutdown() || executorService.isTerminated()) {
            executorService = Executors.newSingleThreadExecutor();
        }
        return this.reconnect();
    }

    private ChannelFuture reconnect() {
        ChannelFuture channelFuture = pool.bootstrap.connect(host, port);
        channel = channelFuture.channel();
        channel.closeFuture().addListener(listener -> {
            if (pool.group.isShuttingDown() || pool.group.isShutdown() || pool.group.isTerminated()) {
                executorService.shutdownNow();
            } else if (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                executorService.execute(this::reconnect);
            }
        });
        return channelFuture;
    }

    public ChannelFuture close() {
        executorService.shutdownNow();
        return channel.close();
    }
}
