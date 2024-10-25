package com.github.h1m3k0.network.proxy.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Getter
@Accessors(chain = true, fluent = true)
@AllArgsConstructor
public enum ProxyPacketType {
    Data(0),
    Register(1),
    UnRegister(2),
    Connect(3),
    Disconnect(4),
    Undefined(null),
    ;

    private final Integer code;
    private static final Map<Integer, ProxyPacketType> map = new HashMap<>();

    static {
        for (ProxyPacketType type : ProxyPacketType.values()) {
            map.put(type.code, type);
        }
    }

    public static ProxyPacketType get(int code) {
        if (map.containsKey(code)) {
            return map.get(code);
        }
        return Undefined;
    }
}
