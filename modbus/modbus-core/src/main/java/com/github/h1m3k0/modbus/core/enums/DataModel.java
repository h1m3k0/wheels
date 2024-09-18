package com.github.h1m3k0.modbus.core.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DataModel {
    Coils(0),
    DiscreteInput(1),
    HoldingRegisters(4),
    InputRegisters(3),
    ;
    private final int address;
}
