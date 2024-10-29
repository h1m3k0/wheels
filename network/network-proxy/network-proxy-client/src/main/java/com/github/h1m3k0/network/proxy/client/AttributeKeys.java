package com.github.h1m3k0.network.proxy.client;

import com.github.h1m3k0.network.proxy.common.MessageKey;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Map;

public class AttributeKeys {
    public static final AttributeKey<String> thisWorkerHost = AttributeKey.valueOf("thisWorkerHost");
    public static final AttributeKey<Integer> thisWorkerPort = AttributeKey.valueOf("thisWorkerPort");
    public static final AttributeKey<Map<MessageKey, Channel>> workerChannelMap = AttributeKey.valueOf("workerChannelMap");
    public static final AttributeKey<MessageKey> workerKey = AttributeKey.valueOf("workerKey");
    public static final AttributeKey<Channel> bossChannel = AttributeKey.valueOf("bossChannel");
}
