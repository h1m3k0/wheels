package com.github.h1m3k0.network.proxy.client;

import com.github.h1m3k0.common.netty.client.Config;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.experimental.Accessors;


@Getter
@Accessors(chain = true, fluent = true)
public class WorkerConfig extends Config<WorkerConfig, WorkerClient, WorkerClientPool> {
    private final Channel bossChannel;
    private final String key;

    public WorkerConfig(String host, int port, Channel bossChannel, String key) {
        super(host, port);
        this.bossChannel = bossChannel;
        this.key = key;
    }
}
