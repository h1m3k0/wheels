package com.github.h1m3k0.modbus.server.functional;

@FunctionalInterface
public interface WriteSingleCoilFunctional {
    void write(int address, boolean value);
}
