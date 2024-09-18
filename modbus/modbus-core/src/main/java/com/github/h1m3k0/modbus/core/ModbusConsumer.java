package com.github.h1m3k0.modbus.core;

import com.github.h1m3k0.modbus.core.request.ModbusRequest;

@FunctionalInterface
public interface ModbusConsumer<REQ extends ModbusRequest<?, REQ, ?, ?>> {
    void accept(REQ req) throws ModbusException;
}
