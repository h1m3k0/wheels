package com.github.h1m3k0.network.proxy.common;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Map;

public class AttributeKeys {
    public static final AttributeKey<Channel> channelKey = AttributeKey.valueOf("channel");
    public static final AttributeKey<String> thisKey = AttributeKey.valueOf("this");
    public static final AttributeKey<Map<String, Channel>> channelMapKey = AttributeKey.valueOf("channelMap");
    public static final AttributeKey<Integer> portKey = AttributeKey.valueOf("port");
}
