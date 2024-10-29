package com.github.h1m3k0.network.proxy.client;

import com.github.h1m3k0.common.netty.client.ClientPool;
import com.github.h1m3k0.network.proxy.client.handler.WorkerClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

public class WorkerClientPool extends ClientPool<WorkerConfig, WorkerClient, WorkerClientPool> {
    public WorkerClientPool() {
        WorkerClientHandler handler = new WorkerClientHandler();
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) {
                ch.pipeline().addLast(handler);
            }
        });
    }

    @Override
    public WorkerClient newClient(WorkerConfig config) {
        return new WorkerClient(this, config);
    }
}
