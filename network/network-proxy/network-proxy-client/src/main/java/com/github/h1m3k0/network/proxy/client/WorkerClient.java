package com.github.h1m3k0.network.proxy.client;

import com.github.h1m3k0.common.netty.client.Client;
import com.github.h1m3k0.network.proxy.common.ConnectMessage;
import com.github.h1m3k0.network.proxy.common.DisconnectMessage;
import io.netty.channel.Channel;

public class WorkerClient extends Client<WorkerConfig, WorkerClient, WorkerClientPool> {
    public WorkerClient(WorkerClientPool pool, WorkerConfig config) {
        super(pool, config.host(), config.port());
        addConnectFutureListener(future -> {
            if (future.isSuccess()) {
                channel.attr(AttributeKeys.bossChannel).set(config.bossChannel());
                channel.attr(AttributeKeys.workerKey).set(config.key());
                config.bossChannel().attr(AttributeKeys.workerChannelMap).get().put(config.key(), channel);
                config.bossChannel().writeAndFlush(new ConnectMessage(config.key()).toBuf());
            } else {
                config.bossChannel().writeAndFlush(new DisconnectMessage(config.key()).toBuf());
            }
        });
        addCloseFutureListener(future->{
            Channel bossChannel = channel.attr(AttributeKeys.bossChannel).get();
            bossChannel.writeAndFlush(new DisconnectMessage(config.key()).toBuf());
        });
    }
}
