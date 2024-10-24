package com.github.h1m3k0.network.proxy.server;

import com.github.h1m3k0.network.proxy.common.Message;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.UUID;

public class JieServer {
    public static final AttributeKey<Channel> channelKey = AttributeKey.valueOf("channel");
    public static final AttributeKey<String> thisKey = AttributeKey.valueOf("this");
    public static final AttributeKey<Map<String, Channel>> channelMapKey = AttributeKey.valueOf("channelMap");
    private final ServerBootstrap bootstrap = new ServerBootstrap()
            .channel(NioServerSocketChannel.class)
            .group(new NioEventLoopGroup(), new NioEventLoopGroup())
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            String key = UUID.randomUUID().toString();
                            ctx.channel().attr(thisKey).set(key);
                            ctx.channel().attr(channelKey).get().attr(channelMapKey).get().put(key, ctx.channel());
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            // 客户 => 云
                            Channel channel = ctx.channel().attr(channelKey).get();
                            String key = ctx.channel().attr(thisKey).get();
                            Message message = new Message(null);
                            channel.writeAndFlush(message);
                        }
                    });
                }
            });

    public void bind(int port, Channel channel) {
        bootstrap.bind(port).syncUninterruptibly().channel().attr(channelKey).set(channel);
    }
}
