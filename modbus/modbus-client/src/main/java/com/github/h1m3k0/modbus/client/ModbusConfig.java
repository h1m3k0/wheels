package com.github.h1m3k0.modbus.client;

import com.github.h1m3k0.common.netty.client.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class ModbusConfig extends Config<ModbusConfig, ModbusClient, ModbusClientPool> {
    private int maxNumber = 128;
    private Byte slaveId;
    private long requestTimeout = 10000;
    private long responseTimeout = 10000;

    public ModbusConfig(String host) {
        this(host, 502);
    }

    public ModbusConfig(String host, int port) {
        super(host, port);
    }
}
