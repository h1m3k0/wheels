package com.github.h1m3k0.modbus.core.function;

import com.github.h1m3k0.modbus.core.enums.FunctionErrorCode;
import com.github.h1m3k0.modbus.core.errorres.WriteSingleCoilError;
import com.github.h1m3k0.modbus.core.request.WriteSingleCoilRequest;
import com.github.h1m3k0.modbus.core.response.WriteSingleCoilResponse;
import com.github.h1m3k0.modbus.core.enums.FunctionCode;

public class WriteSingleCoilFunction extends ModbusFunction<WriteSingleCoilFunction, WriteSingleCoilRequest, WriteSingleCoilResponse, WriteSingleCoilError> {
    private WriteSingleCoilFunction() {
        super(
                FunctionCode.WriteSingleCoil,
                FunctionErrorCode.WriteSingleCoil
        );
    }

    private static final WriteSingleCoilFunction Instance = new WriteSingleCoilFunction();

    public static WriteSingleCoilFunction Instance() {
        return Instance;
    }
}
