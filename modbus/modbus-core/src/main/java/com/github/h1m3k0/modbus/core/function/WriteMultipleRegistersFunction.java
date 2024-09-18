package com.github.h1m3k0.modbus.core.function;

import com.github.h1m3k0.modbus.core.request.WriteMultipleRegistersRequest;
import com.github.h1m3k0.modbus.core.enums.FunctionCode;
import com.github.h1m3k0.modbus.core.enums.FunctionErrorCode;
import com.github.h1m3k0.modbus.core.errorres.WriteMultipleRegistersError;
import com.github.h1m3k0.modbus.core.response.WriteMultipleRegistersResponse;

public class WriteMultipleRegistersFunction extends ModbusFunction<WriteMultipleRegistersFunction, WriteMultipleRegistersRequest, WriteMultipleRegistersResponse, WriteMultipleRegistersError> {
    private WriteMultipleRegistersFunction() {
        super(
                FunctionCode.WriteMultipleRegisters,
                FunctionErrorCode.WriteMultipleRegisters
        );
    }

    private static final WriteMultipleRegistersFunction Instance = new WriteMultipleRegistersFunction();

    public static WriteMultipleRegistersFunction Instance() {
        return Instance;
    }
}
