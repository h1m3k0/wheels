package com.github.h1m3k0.modbus.server.functional;

@FunctionalInterface
public interface WriteSingleRegisterFunctional {
    void write(int address, short value);
}
