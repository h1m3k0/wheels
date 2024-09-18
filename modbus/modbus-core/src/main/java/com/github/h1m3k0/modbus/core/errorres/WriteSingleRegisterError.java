package com.github.h1m3k0.modbus.core.errorres;

import com.github.h1m3k0.modbus.core.enums.ExceptionCode;
import com.github.h1m3k0.modbus.core.function.WriteSingleRegisterFunction;
import com.github.h1m3k0.modbus.core.request.WriteSingleRegisterRequest;
import com.github.h1m3k0.modbus.core.response.WriteSingleRegisterResponse;

public class WriteSingleRegisterError extends ModbusError<WriteSingleRegisterFunction, WriteSingleRegisterRequest, WriteSingleRegisterResponse, WriteSingleRegisterError> {
    public WriteSingleRegisterError(ExceptionCode exceptionCode) {
        super(exceptionCode);
        super.function = WriteSingleRegisterFunction.Instance();
    }
}
