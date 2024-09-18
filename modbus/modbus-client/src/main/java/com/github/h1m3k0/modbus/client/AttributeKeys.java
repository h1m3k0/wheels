package com.github.h1m3k0.modbus.client;

import com.github.h1m3k0.modbus.core.ModbusMessage;
import io.netty.util.AttributeKey;

import java.util.concurrent.CountDownLatch;

public class AttributeKeys {
    public static AttributeKey<Integer> MessageCount() {
        return AttributeKey.valueOf("com.github.h1m3k0.modbus.MessageCount");
    }

    public static AttributeKey<CountDownLatch> Lock(int msgCount) {
        return AttributeKey.valueOf("com.github.h1m3k0.modbus.Lock-" + msgCount);
    }

    public static AttributeKey<ModbusMessage<?, ?, ?, ?, ?>> Message(int msgCount) {
        return AttributeKey.valueOf("com.github.h1m3k0.modbus.Message-" + msgCount);
    }
}
