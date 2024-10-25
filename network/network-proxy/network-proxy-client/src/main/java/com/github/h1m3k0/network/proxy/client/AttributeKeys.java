package com.github.h1m3k0.network.proxy.client;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Map;

public class AttributeKeys {
    public static final AttributeKey<String> thisWorkerHost = AttributeKey.valueOf("thisWorkerHost");
    public static final AttributeKey<Integer> thisWorkerPort = AttributeKey.valueOf("thisWorkerPort");
    public static final AttributeKey<Integer> targetWorkerPort = AttributeKey.valueOf("targetWorkerPort");
    public static final AttributeKey<Map<String, Channel>> workerChannelMap = AttributeKey.valueOf("workerChannelMap");
    public static final AttributeKey<String> workerKey = AttributeKey.valueOf("workerKey");
    public static final AttributeKey<Channel> bossChannel = AttributeKey.valueOf("bossChannel");
}
