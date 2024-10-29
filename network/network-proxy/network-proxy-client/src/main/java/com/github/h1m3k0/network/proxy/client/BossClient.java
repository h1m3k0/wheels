package com.github.h1m3k0.network.proxy.client;

import com.github.h1m3k0.common.netty.client.RcClient;
import com.github.h1m3k0.network.proxy.common.RegisterMessage;

public class BossClient extends RcClient<BossConfig, BossClient, BossClientPool> {
    public BossClient(BossClientPool pool, BossConfig config) {
        super(pool, config.host(), config.port());
        addConnectFutureListener(future -> {
            if (future.isSuccess()) {
                channel.attr(AttributeKeys.thisWorkerHost).set(config.thisWorkerHost());
                channel.attr(AttributeKeys.thisWorkerPort).set(config.thisWorkerPort());
                channel.writeAndFlush(new RegisterMessage(config.targetWorkerPort()).toBuf());
            }
        });
    }
}
