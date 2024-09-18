package com.github.h1m3k0.modbus.server.functional;

@FunctionalInterface
public interface ReadCoilsFunctional {
    boolean[] read(int address, int quantity);
}
