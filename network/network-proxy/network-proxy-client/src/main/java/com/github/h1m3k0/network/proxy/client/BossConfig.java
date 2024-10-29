package com.github.h1m3k0.network.proxy.client;

import com.github.h1m3k0.common.netty.client.Config;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class BossConfig extends Config<BossConfig, BossClient, BossClientPool> {
    private String thisWorkerHost;
    private int thisWorkerPort;
    private int targetWorkerPort;

    public BossConfig(String targetBossHost, int targetBossPort,
                      String thisWorkerHost, int thisWorkerPort, int targetWorkerPort) {
        super(targetBossHost, targetBossPort);
        this.thisWorkerHost = thisWorkerHost;
        this.thisWorkerPort = thisWorkerPort;
        this.targetWorkerPort = targetWorkerPort;
    }

    public BossConfig(String targetBossHost, int targetBossPort,
                      int thisWorkerPort, int targetWorkerPort) {
        this(targetBossHost, targetBossPort, "localhost", thisWorkerPort, targetWorkerPort);
    }
}
