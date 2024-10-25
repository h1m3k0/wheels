package com.github.h1m3k0.network.proxy.server;

import com.github.h1m3k0.network.proxy.common.AttributeKeys;
import com.github.h1m3k0.network.proxy.common.Message;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class JieServer {
    private final Map<Integer, Channel> channelMap = new HashMap<>();
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
                            Channel thisChannel = ctx.channel();
                            thisChannel.attr(AttributeKeys.thisKey).set(key);
                            InetSocketAddress address = (InetSocketAddress) ctx.channel().localAddress();
                            Channel channel = channelMap.get(address.getPort());
                            thisChannel.attr(AttributeKeys.channelKey).set(channel);
                            channel.attr(AttributeKeys.channelMapKey).get().put(key, thisChannel);
                            Message message = new Message();
                            message.setType(1);
                            message.setKey(key);
                            channel.writeAndFlush(Unpooled.wrappedBuffer(message.toBytes()));
                        }

                        @Override
                        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                            String key = ctx.channel().attr(AttributeKeys.thisKey).get();
                            Message message = new Message();
                            message.setType(2);
                            message.setKey(key);
                            Channel channel = ctx.channel().attr(AttributeKeys.channelKey).get();
                            channel.writeAndFlush(Unpooled.wrappedBuffer(message.toBytes()));
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            // 客户 => 云
                            Channel channel = ctx.channel().attr(AttributeKeys.channelKey).get();
                            String key = ctx.channel().attr(AttributeKeys.thisKey).get();
                            Message message = new Message();
                            message.setKey(key);
                            ByteBuf buf = (ByteBuf) msg;
                            byte[] bytes = new byte[buf.readableBytes()];
                            buf.readBytes(bytes);
                            message.setMessage(new String(bytes, StandardCharsets.UTF_8));
                            channel.writeAndFlush(Unpooled.wrappedBuffer(message.toBytes()));
                        }
                    });
                }
            });

    public void bind(int port, Channel channel) throws ExecutionException, InterruptedException {
        ChannelFuture channelFuture = bootstrap.bind(port);
        channelMap.put(port, channel);
        channel.attr(AttributeKeys.channelKey).set(channelFuture.channel());
        channelFuture.get();
    }
}
