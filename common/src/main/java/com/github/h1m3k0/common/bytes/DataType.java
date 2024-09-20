package com.github.h1m3k0.common.bytes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(chain = true, fluent = true)
public enum DataType {
    bit0(NumberType.BIT, 1),
    bit1(NumberType.BIT, 1),
    bit2(NumberType.BIT, 1),
    bit3(NumberType.BIT, 1),
    bit4(NumberType.BIT, 1),
    bit5(NumberType.BIT, 1),
    bit6(NumberType.BIT, 1),
    bit7(NumberType.BIT, 1),
    int8(NumberType.INT, 1),
    int16(NumberType.INT, 2),
    int32(NumberType.INT, 4),
    int64(NumberType.INT, 8),
    uint8(NumberType.UINT, 1),
    uint16(NumberType.UINT, 2),
    uint32(NumberType.UINT, 4),
    uint64(NumberType.UINT, 4),
    float32(NumberType.FLOAT, 4),
    float64(NumberType.FLOAT, 8),
    ;
    private final NumberType type;
    private final int length;

    public enum NumberType {
        BIT, INT, UINT, FLOAT,
        ;
    }
}
