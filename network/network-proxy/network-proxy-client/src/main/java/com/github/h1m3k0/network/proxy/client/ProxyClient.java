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
import java.util.HashMap;
import java.util.UUID;

public class ProxyClient {
    private final FaClient faClient = new FaClient();
    private final Bootstrap bootstrap = new Bootstrap().channel(NioSocketChannel.class)
            .group(new NioEventLoopGroup())
            .handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            Message message = new Message();
                            message.setType(1);
                            message.setKey(UUID.randomUUID().toString());
                            message.setMessage(ctx.channel().attr(AttributeKeys.portKey).get().toString());
                            ctx.channel().writeAndFlush(Unpooled.wrappedBuffer(message.toBytes()));  // login
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            // 云 => this
                            if (msg instanceof ByteBuf) {
                                ByteBuf buf = (ByteBuf) msg;
                                byte[] bytes = new byte[buf.readableBytes()];
                                buf.readBytes(bytes);
                                Message message = new Message(bytes);
                                String key = message.getKey();
                                if (message.getType() == 1) {
                                    ctx.channel().attr(AttributeKeys.channelMapKey).set(new HashMap<>());
                                    faClient.connect("127.0.0.1", 12302, ctx.channel(), key);
                                } else {
                                    String data = message.getMessage();
                                    byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
                                    Channel channel = ctx.channel().attr(AttributeKeys.channelMapKey).get().get(key);
                                    channel.writeAndFlush(Unpooled.wrappedBuffer(dataBytes));
                                }
                            }
                        }
                    });
                }
            });
    public void connect(String host, int port, int thisPort) {
        ChannelFuture channelFuture = bootstrap.connect(host, port);
        channelFuture.channel().attr(AttributeKeys.portKey).set(thisPort);
        channelFuture.syncUninterruptibly();
    }
}
