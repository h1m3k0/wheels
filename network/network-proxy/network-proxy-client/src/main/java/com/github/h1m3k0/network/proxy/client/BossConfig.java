package com.github.h1m3k0.network.proxy.client;

import com.github.h1m3k0.common.netty.client.Config;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class BossConfig extends Config<BossConfig, BossClient, BossClientPool> {

    public BossConfig(String targetBossHost, int targetBossPort) {
        super(targetBossHost, targetBossPort);
    }
}
