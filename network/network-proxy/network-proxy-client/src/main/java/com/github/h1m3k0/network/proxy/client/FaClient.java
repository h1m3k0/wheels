package com.github.h1m3k0.network.proxy.client;

import com.github.h1m3k0.network.proxy.common.AttributeKeys;
import com.github.h1m3k0.network.proxy.common.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;

public class FaClient {
    private final Bootstrap bootstrap = new Bootstrap().channel(NioSocketChannel.class)
            .group(new NioEventLoopGroup())
            .handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            Channel thisChannel = ctx.channel();
                            String key = thisChannel.attr(AttributeKeys.thisKey).get();
                            thisChannel.attr(AttributeKeys.channelKey).get().attr(AttributeKeys.channelMapKey).get().put(key, thisChannel);
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            // 本地 => this
                            Message message = new Message();
                            message.setKey(ctx.channel().attr(AttributeKeys.thisKey).get());
                            ByteBuf buf = (ByteBuf) msg;
                            byte[] bytes = new byte[buf.readableBytes()];
                            buf.readBytes(bytes);
                            message.setMessage(new String(bytes, StandardCharsets.UTF_8));
                            ctx.channel().attr(AttributeKeys.channelKey).get().writeAndFlush(Unpooled.wrappedBuffer(message.toBytes()));
                        }
                    });
                }
            });
    public void connect(String host, int port, Channel channel, String key) {
        ChannelFuture channelFuture = bootstrap.connect(host, port);
        Channel thisChannel = channelFuture.channel();
        thisChannel.attr(AttributeKeys.channelKey).set(channel);
        thisChannel.attr(AttributeKeys.thisKey).set(key);
        channelFuture.syncUninterruptibly();

    }
}
