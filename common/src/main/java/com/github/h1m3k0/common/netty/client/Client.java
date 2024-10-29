package com.github.h1m3k0.common.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Client<config extends Config<config, client, pool>, client extends Client<config, client, pool>, pool extends ClientPool<config, client, pool>> implements AutoCloseable {
    protected final pool pool;
    protected final String host;
    protected final int port;
    protected final List<GenericFutureListener<? extends Future<? super Void>>> connectFutureListeners = new CopyOnWriteArrayList<>();
    protected final List<GenericFutureListener<? extends Future<? super Void>>> closeFutureListeners = new CopyOnWriteArrayList<>();
    protected Channel channel;
    protected boolean working;

    public Client(pool pool, String host, int port) {
        this.pool = pool;
        this.host = host;
        this.port = port;
    }

    public ChannelFuture connect() {
        working = true;
        if (channel != null && channel.isOpen()) {
            this.close();
        }
        ChannelFuture channelFuture = pool.bootstrap.connect(host, port);
        channel = channelFuture.channel();
        connectFutureListeners.forEach(channelFuture::addListener);
        closeFutureListeners.forEach(channel.closeFuture()::addListener);
        return channelFuture;
    }

    private synchronized ChannelFuture reconnect() {
        ChannelFuture channelFuture = pool.bootstrap.connect(host, port);
        channel = channelFuture.channel();
        channel.closeFuture().addListener(listener -> {
            if (pool.group.isShuttingDown() || pool.group.isShutdown() || pool.group.isTerminated()) {
                working = false;
            } else if (working) {
                this.connect();
            }
        });
        return channelFuture;
    }

    @Override
    public void close() {
        working = false;
        if (channel != null) {
            channel.close();
        }
    }

    public void addConnectFutureListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        connectFutureListeners.add(listener);
    }

    public void removeConnectFutureListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        connectFutureListeners.remove(listener);
    }

    public void addCloseFutureListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        if (channel != null) {
            channel.closeFuture().addListener(listener);
        }
        closeFutureListeners.add(listener);
    }

    public void removeCloseFutureListener(GenericFutureListener<? extends Future<? super Void>> listener) {
        if (channel != null) {
            channel.closeFuture().removeListener(listener);
        }
        closeFutureListeners.remove(listener);
    }
}
