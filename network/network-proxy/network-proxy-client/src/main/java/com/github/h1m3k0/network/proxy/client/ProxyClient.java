package com.github.h1m3k0.network.proxy.client;

import com.github.h1m3k0.network.proxy.common.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ProxyClient {
    private final Bootstrap bootstrap = new Bootstrap().channel(NioSocketChannel.class)
            .group(new NioEventLoopGroup())
            .handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ctx.channel().writeAndFlush(new Message(null));  // login
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            // 云 => this
                        }
                    });
                }
            });
    public void connect(String host, int port) {
        bootstrap.connect(host, port).syncUninterruptibly();
    }
}
