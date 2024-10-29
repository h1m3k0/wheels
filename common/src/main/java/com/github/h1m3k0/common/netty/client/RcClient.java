package com.github.h1m3k0.common.netty.client;

/**
 * 断线重连的client
 */
public abstract class RcClient<config extends Config<config, client, pool>, client extends Client<config, client, pool>, pool extends ClientPool<config, client, pool>> extends Client<config, client, pool> {
    public RcClient(pool pool, String host, int port) {
        super(pool, host, port);
        addCloseFutureListener(listener -> {
            if (pool.group.isShuttingDown() || pool.group.isShutdown() || pool.group.isTerminated()) {
                working = false;
            } else if (working) {
                this.connect();
            }
        });
    }
}
