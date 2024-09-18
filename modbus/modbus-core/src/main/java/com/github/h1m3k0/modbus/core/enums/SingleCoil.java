package com.github.h1m3k0.modbus.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Accessors(chain = true, fluent = true)
public enum SingleCoil {
    ON(0xFF00, true),
    OFF(0x0000, false),
    Undefined(null, null),
    ;
    private final Integer intValue;
    private final Boolean boolValue;
    private static final Map<Integer, SingleCoil> intMap = Arrays.stream(SingleCoil.values()).collect(Collectors.toMap(SingleCoil::intValue, singleCoil -> singleCoil));
    private static final Map<Boolean, SingleCoil> boolMap = Arrays.stream(SingleCoil.values()).collect(Collectors.toMap(SingleCoil::boolValue, singleCoil -> singleCoil));

    public static SingleCoil get(int value) {
        if (intMap.containsKey(value)) {
            return intMap.get(value);
        }
        return Undefined;
    }

    public static SingleCoil get(boolean value) {
        if (boolMap.containsKey(value)) {
            return boolMap.get(value);
        }
        return Undefined;
    }
}
