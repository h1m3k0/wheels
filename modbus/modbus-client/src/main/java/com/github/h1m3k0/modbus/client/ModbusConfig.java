package com.github.h1m3k0.modbus.client;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true, fluent = true)
public class ModbusConfig {
    private final String host;
    private final int port;
    private int maxNumber = 128;
    private Byte slaveId;
    private long requestTimeout = 10000;
    private long responseTimeout = 10000;

    public ModbusConfig(String host) {
        this.host = host;
        this.port = 502;
    }

    public ModbusConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }
}
