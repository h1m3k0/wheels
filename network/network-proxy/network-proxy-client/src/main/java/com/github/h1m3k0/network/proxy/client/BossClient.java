package com.github.h1m3k0.network.proxy.client;

import com.github.h1m3k0.network.proxy.client.handler.BossClientHandler;
import com.github.h1m3k0.network.proxy.common.ProxyPacketDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ExecutionException;

public class BossClient {
    private final WorkerClient workerClient = new WorkerClient();
    private final Bootstrap bootstrap = new Bootstrap();
    private final String targetHost;
    private final int targetBossPort;

    /**
     * @param targetHost     server主服务的host地址
     * @param targetBossPort server主服务的端口号
     */
    public BossClient(String targetHost, int targetBossPort) {
        this.targetHost = targetHost;
        this.targetBossPort = targetBossPort;
        ProxyPacketDecoder decoder = new ProxyPacketDecoder();
        bootstrap.channel(NioSocketChannel.class).group(new NioEventLoopGroup()).handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(decoder);
                ch.pipeline().addLast(new BossClientHandler(workerClient));
            }
        });
    }

    /**
     * 注册代理
     *
     * @param thisWorkerHost   本地目标服务的host地址
     * @param thisWorkerPort   本地目标服务的端口号
     * @param targetWorkerPort server服务映射的端口号
     */
    public void register(String thisWorkerHost, int thisWorkerPort, int targetWorkerPort) {
        ChannelFuture channelFuture = bootstrap.connect(targetHost, targetBossPort);
        channelFuture.channel().attr(AttributeKeys.thisWorkerHost).set(thisWorkerHost);
        channelFuture.channel().attr(AttributeKeys.thisWorkerPort).set(thisWorkerPort);
        channelFuture.channel().attr(AttributeKeys.targetWorkerPort).set(targetWorkerPort);
        channelFuture.syncUninterruptibly();
    }

    public void register(int thisWorkPort, int targetBossPort) {
        register("localhost", thisWorkPort, targetBossPort);
    }
}
