package com.github.h1m3k0.modbus.server.functional;

@FunctionalInterface
public interface WriteMultipleRegistersFunctional {
    void write(int address, byte[] value);
}
