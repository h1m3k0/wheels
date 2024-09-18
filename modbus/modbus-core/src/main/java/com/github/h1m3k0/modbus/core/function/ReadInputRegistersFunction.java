package com.github.h1m3k0.modbus.core.function;

import com.github.h1m3k0.modbus.core.errorres.ReadInputRegistersError;
import com.github.h1m3k0.modbus.core.response.ReadInputRegistersResponse;
import com.github.h1m3k0.modbus.core.enums.FunctionCode;
import com.github.h1m3k0.modbus.core.enums.FunctionErrorCode;
import com.github.h1m3k0.modbus.core.request.ReadInputRegistersRequest;

public class ReadInputRegistersFunction extends ModbusFunction<ReadInputRegistersFunction, ReadInputRegistersRequest, ReadInputRegistersResponse, ReadInputRegistersError> {
    private ReadInputRegistersFunction() {
        super(
                FunctionCode.ReadInputRegisters,
                FunctionErrorCode.ReadInputRegisters
        );
    }

    private static final ReadInputRegistersFunction Instance = new ReadInputRegistersFunction();

    public static ReadInputRegistersFunction Instance() {
        return Instance;
    }
}
