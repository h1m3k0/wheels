package com.github.h1m3k0.modbus.core.function;

import com.github.h1m3k0.modbus.core.errorres.ReadWriteMultipleRegistersError;
import com.github.h1m3k0.modbus.core.enums.FunctionCode;
import com.github.h1m3k0.modbus.core.enums.FunctionErrorCode;
import com.github.h1m3k0.modbus.core.request.ReadWriteMultipleRegistersRequest;
import com.github.h1m3k0.modbus.core.response.ReadWriteMultipleRegistersResponse;

public class ReadWriteMultipleRegistersFunction extends ModbusFunction<ReadWriteMultipleRegistersFunction, ReadWriteMultipleRegistersRequest, ReadWriteMultipleRegistersResponse, ReadWriteMultipleRegistersError> {
    private ReadWriteMultipleRegistersFunction() {
        super(
                FunctionCode.ReadWriteMultipleRegisters,
                FunctionErrorCode.ReadWriteMultipleRegisters
        );
    }

    private static final ReadWriteMultipleRegistersFunction Instance = new ReadWriteMultipleRegistersFunction();

    public static ReadWriteMultipleRegistersFunction Instance() {
        return Instance;
    }
}
