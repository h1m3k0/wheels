package com.github.h1m3k0.modbus.client.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(chain = true, fluent = true)
public enum DataType {
    i8(NumberType.INT, 1),
    i16(NumberType.INT, 2),
    i32(NumberType.INT, 4),
    i64(NumberType.INT, 8),
    u8(NumberType.UINT, 1),
    u16(NumberType.UINT, 2),
    u32(NumberType.UINT, 4),
    f32(NumberType.FLOAT, 4),
    f64(NumberType.FLOAT, 8),
    ;
    private final NumberType type;
    private final int length;

    public enum NumberType {
        UINT, INT, FLOAT,
        ;
    }
}
