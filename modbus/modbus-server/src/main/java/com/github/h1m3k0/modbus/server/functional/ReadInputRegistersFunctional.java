package com.github.h1m3k0.modbus.server.functional;

@FunctionalInterface
public interface ReadInputRegistersFunctional {
    byte[] read(int address, int quantity);
}
