package com.github.h1m3k0.network.proxy.client;

import com.github.h1m3k0.common.netty.client.ClientPool;
import com.github.h1m3k0.network.proxy.client.handler.BossClientHandler;
import com.github.h1m3k0.network.proxy.common.ProxyMessageDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class BossClientPool extends ClientPool<BossConfig, BossClient, BossClientPool> {

    public BossClientPool() {
        ProxyMessageDecoder decoder = new ProxyMessageDecoder();
        BossClientHandler handler = new BossClientHandler(new WorkerClient());
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(10240, 0, 4));
                ch.pipeline().addLast(decoder);
                ch.pipeline().addLast(handler);
            }
        });
    }

    @Override
    public BossClient newClient(BossConfig config) {
        return new BossClient(this, config);
    }
}
