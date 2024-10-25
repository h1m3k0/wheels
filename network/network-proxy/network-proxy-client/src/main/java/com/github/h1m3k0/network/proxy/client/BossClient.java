package com.github.h1m3k0.network.proxy.client;

import com.github.h1m3k0.common.netty.client.Client;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public class BossClient extends Client<BossConfig, BossClient, BossClientPool> {
    private final BossConfig config;

    public BossClient(BossClientPool pool, BossConfig config) {
        super(pool, config.host(), config.port());
        this.config = config;
    }

    public ChannelFuture connect() {
        ChannelFuture channelFuture = super.connect();
        Channel bossChannel = channelFuture.channel();
        bossChannel.attr(AttributeKeys.thisWorkerHost).set(config.thisWorkerHost());
        bossChannel.attr(AttributeKeys.thisWorkerPort).set(config.thisWorkerPort());
        bossChannel.attr(AttributeKeys.targetWorkerPort).set(config.targetWorkerPort());
        return channelFuture;
    }
}
