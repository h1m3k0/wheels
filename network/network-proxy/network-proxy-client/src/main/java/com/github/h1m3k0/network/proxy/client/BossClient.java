package com.github.h1m3k0.network.proxy.client;

import com.github.h1m3k0.common.netty.client.RcClient;
import com.github.h1m3k0.network.proxy.common.RegisterMessage;

public class BossClient extends RcClient<BossConfig, BossClient, BossClientPool> {
    public BossClient(BossClientPool pool, BossConfig config) {
        super(pool, config.host(), config.port());
    }

    public void register(int thisWorkPort, int targetWorkPort) {
        register("localhost", thisWorkPort, targetWorkPort);
    }

    public void register(String thisWorkerHost, int thisWorkerPort, int targetWorkerPort) {
        if (working && channel != null) {
            channel.attr(AttributeKeys.thisWorkerHost).set(thisWorkerHost);
            channel.attr(AttributeKeys.thisWorkerPort).set(thisWorkerPort);
            channel.writeAndFlush(new RegisterMessage(targetWorkerPort).toBuf());
        }
    }
}
