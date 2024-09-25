package com.github.h1m3k0.common.netty.client;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true, fluent = true)
public abstract class Config<config extends Config<config, client, pool>, client extends Client<config, client, pool>, pool extends ClientPool<config, client, pool>> {
    protected final String host;
    protected final int port;

    public Config(final String host, final int port) {
        this.host = host;
        this.port = port;
    }
}
