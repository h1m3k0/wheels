package com.github.h1m3k0.network.proxy.client.handler;

import com.github.h1m3k0.network.proxy.client.AttributeKeys;
import com.github.h1m3k0.network.proxy.client.WorkerClientPool;
import com.github.h1m3k0.network.proxy.client.WorkerConfig;
import com.github.h1m3k0.network.proxy.common.*;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

@ChannelHandler.Sharable
public class BossClientHandler extends SimpleChannelInboundHandler<ProxyMessage> {
    private final WorkerClientPool workerClientPool;

    public BossClientHandler(WorkerClientPool workerClientPool) {
        this.workerClientPool = workerClientPool;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProxyMessage proxyMessage) throws Exception {
        Channel bossChannel = ctx.channel();
        switch (proxyMessage.type()) {
            case Data: {
                DataMessage message = (DataMessage) proxyMessage;
                Channel workerChannel = bossChannel.attr(AttributeKeys.workerChannelMap).get().get(message.key());
                workerChannel.writeAndFlush(Unpooled.wrappedBuffer(message.data()));
                break;
            }
            case UnRegister: {
                UnRegisterMessage message = (UnRegisterMessage) proxyMessage;
                System.out.println(message.port() + "注册失败");

                break;
            }
            case Connect: {
                ConnectMessage message = (ConnectMessage) proxyMessage;
                workerClientPool.newClient(new WorkerConfig(
                                bossChannel.attr(AttributeKeys.thisWorkerHost).get(),
                                bossChannel.attr(AttributeKeys.thisWorkerPort).get(),
                                bossChannel, message.key()))
                        .connect();
                break;
            }
            case Disconnect: {
                DisconnectMessage message = (DisconnectMessage) proxyMessage;
                Map<MessageKey, Channel> workerChannelMap = bossChannel.attr(AttributeKeys.workerChannelMap).get();
                Channel workerChannel = workerChannelMap.remove(message.key());
                if (workerChannel != null) {
                    workerChannel.close();
                }
                break;
            }
        }
    }
}
