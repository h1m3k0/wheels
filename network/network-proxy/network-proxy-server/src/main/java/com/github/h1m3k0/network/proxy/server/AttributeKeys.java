package com.github.h1m3k0.network.proxy.server;

import com.github.h1m3k0.network.proxy.common.MessageKey;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Map;

public class AttributeKeys {
    public static final AttributeKey<Map<MessageKey, Channel>> workerChannelMap = AttributeKey.valueOf("workerChannelMap");
    public static final AttributeKey<Channel> linkChannel = AttributeKey.valueOf("linkChannel");
    public static final AttributeKey<MessageKey> workerKey = AttributeKey.valueOf("workerKey");
    public static final AttributeKey<Channel> bossChannel = AttributeKey.valueOf("bossChannel");
}
