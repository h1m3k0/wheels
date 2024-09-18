package com.github.h1m3k0.modbus.core.function;

import com.github.h1m3k0.modbus.core.errorres.ReadCoilsError;
import com.github.h1m3k0.modbus.core.request.ReadCoilsRequest;
import com.github.h1m3k0.modbus.core.enums.FunctionCode;
import com.github.h1m3k0.modbus.core.enums.FunctionErrorCode;
import com.github.h1m3k0.modbus.core.response.ReadCoilsResponse;

public class ReadCoilsFunction extends ModbusFunction<ReadCoilsFunction, ReadCoilsRequest, ReadCoilsResponse, ReadCoilsError> {
    private ReadCoilsFunction() {
        super(
                FunctionCode.ReadCoils,
                FunctionErrorCode.ReadCoils
        );
    }

    private static final ReadCoilsFunction Instance = new ReadCoilsFunction();

    public static ReadCoilsFunction Instance() {
        return Instance;
    }
}
