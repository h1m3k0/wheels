package com.github.h1m3k0.modbus.server.functional;

@FunctionalInterface
public interface WriteMultipleCoilsFunctional {
    void write(int address, boolean[] value);
}
