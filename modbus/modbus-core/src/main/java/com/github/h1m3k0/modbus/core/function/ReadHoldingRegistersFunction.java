package com.github.h1m3k0.modbus.core.function;

import com.github.h1m3k0.modbus.core.enums.FunctionCode;
import com.github.h1m3k0.modbus.core.enums.FunctionErrorCode;
import com.github.h1m3k0.modbus.core.errorres.ReadHoldingRegistersError;
import com.github.h1m3k0.modbus.core.request.ReadHoldingRegistersRequest;
import com.github.h1m3k0.modbus.core.response.ReadHoldingRegistersResponse;

public class ReadHoldingRegistersFunction extends ModbusFunction<ReadHoldingRegistersFunction, ReadHoldingRegistersRequest, ReadHoldingRegistersResponse, ReadHoldingRegistersError> {
    private ReadHoldingRegistersFunction() {
        super(
                FunctionCode.ReadHoldingRegisters,
                FunctionErrorCode.ReadHoldingRegisters
        );
    }

    private static final ReadHoldingRegistersFunction Instance = new ReadHoldingRegistersFunction();

    public static ReadHoldingRegistersFunction Instance() {
        return Instance;
    }
}
