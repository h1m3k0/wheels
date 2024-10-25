package com.github.h1m3k0.network.proxy.server;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Map;

public class AttributeKeys {
    public static final AttributeKey<Map<String, Channel>> workerChannelMap = AttributeKey.valueOf("workerChannelMap");
    public static final AttributeKey<Channel> linkChannel = AttributeKey.valueOf("linkChannel");
    public static final AttributeKey<String> workerKey = AttributeKey.valueOf("workerKey");
    public static final AttributeKey<Channel> bossChannel = AttributeKey.valueOf("bossChannel");
}
