package com.github.h1m3k0.network.proxy.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Getter
@Accessors(chain = true, fluent = true)
@AllArgsConstructor
public enum ProxyType {
    Data(0),
    Register(1),
    Connect(3),
    Disconnect(4),
    Undefined(null),
    ;

    private final Integer code;
    private static final Map<Integer, ProxyType> map = new HashMap<>();

    static {
        for (ProxyType type : ProxyType.values()) {
            map.put(type.code, type);
        }
    }

    public static ProxyType get(int code) {
        if (map.containsKey(code)) {
            return map.get(code);
        }
        return Undefined;
    }
}
