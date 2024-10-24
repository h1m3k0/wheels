package com.github.h1m3k0.network.proxy.server;

import com.github.h1m3k0.network.proxy.common.Message;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.HashMap;

public class ProxyServer {
    private final AttributeKey<Channel> channelKey = AttributeKey.<Channel>valueOf("channel");

    public ProxyServer(final int port) {
        JieServer jieServer = new JieServer();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.channel(NioServerSocketChannel.class)
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                ctx.channel().attr(JieServer.channelMapKey).set(new HashMap<>());
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                // 服务 => 云
                                if (msg instanceof ByteBuf) {
                                    ByteBuf buf = (ByteBuf) msg;
                                    ByteBuf copyBuf = buf.copy();
                                    byte[] bytes = new byte[copyBuf.readableBytes()];
                                    copyBuf.readBytes(bytes);
                                    Message message = new Message(bytes);
                                    if (message.getType() == 1) { // login
                                        int port = Integer.parseInt(message.getMessage());
                                        jieServer.bind(port, ctx.channel());
                                    } else {

                                    }
                                }

                                super.channelRead(ctx, msg);
                            }
                        });
                    }
                });
        try {
            bootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
