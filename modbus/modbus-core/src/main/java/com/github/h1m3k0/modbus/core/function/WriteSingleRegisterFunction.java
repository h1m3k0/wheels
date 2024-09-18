package com.github.h1m3k0.modbus.core.function;

import com.github.h1m3k0.modbus.core.enums.FunctionErrorCode;
import com.github.h1m3k0.modbus.core.errorres.WriteSingleRegisterError;
import com.github.h1m3k0.modbus.core.request.WriteSingleRegisterRequest;
import com.github.h1m3k0.modbus.core.enums.FunctionCode;
import com.github.h1m3k0.modbus.core.response.WriteSingleRegisterResponse;

public class WriteSingleRegisterFunction extends ModbusFunction<WriteSingleRegisterFunction, WriteSingleRegisterRequest, WriteSingleRegisterResponse, WriteSingleRegisterError> {
    private WriteSingleRegisterFunction() {
        super(
                FunctionCode.WriteSingleRegister,
                FunctionErrorCode.WriteSingleRegister
        );
    }

    private static final WriteSingleRegisterFunction Instance = new WriteSingleRegisterFunction();

    public static WriteSingleRegisterFunction Instance() {
        return Instance;
    }
}
