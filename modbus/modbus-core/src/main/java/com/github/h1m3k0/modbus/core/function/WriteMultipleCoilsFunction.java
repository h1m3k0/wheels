package com.github.h1m3k0.modbus.core.function;

import com.github.h1m3k0.modbus.core.errorres.WriteMultipleCoilsError;
import com.github.h1m3k0.modbus.core.enums.FunctionCode;
import com.github.h1m3k0.modbus.core.enums.FunctionErrorCode;
import com.github.h1m3k0.modbus.core.request.WriteMultipleCoilsRequest;
import com.github.h1m3k0.modbus.core.response.WriteMultipleCoilsResponse;

public class WriteMultipleCoilsFunction extends ModbusFunction<WriteMultipleCoilsFunction, WriteMultipleCoilsRequest, WriteMultipleCoilsResponse, WriteMultipleCoilsError> {
    private WriteMultipleCoilsFunction() {
        super(
                FunctionCode.WriteMultipleCoils,
                FunctionErrorCode.WriteMultipleCoils
        );
    }

    private static final WriteMultipleCoilsFunction Instance = new WriteMultipleCoilsFunction();

    public static WriteMultipleCoilsFunction Instance() {
        return Instance;
    }
}
