package com.github.h1m3k0.network.proxy.server;

import com.github.h1m3k0.network.proxy.common.ProxyPacketDecoder;
import com.github.h1m3k0.network.proxy.server.handler.BossServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class BossServer {

    /**
     *
     * @param port 主服务监听的端口号
     */
    public BossServer(final int port) {
        WorkerServer workerServer = new WorkerServer();
        ProxyPacketDecoder decoder = new ProxyPacketDecoder();
        BossServerHandler handler = new BossServerHandler(workerServer);

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.channel(NioServerSocketChannel.class)
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(10240, 0, 4));
                        ch.pipeline().addLast(decoder);
                        ch.pipeline().addLast(handler);
                    }
                });
        try {
            bootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
