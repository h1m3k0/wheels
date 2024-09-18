package com.github.h1m3k0.modbus.core.function;

import com.github.h1m3k0.modbus.core.enums.FunctionErrorCode;
import com.github.h1m3k0.modbus.core.errorres.UndefinedError;
import com.github.h1m3k0.modbus.core.response.UndefinedResponse;
import com.github.h1m3k0.modbus.core.enums.FunctionCode;
import com.github.h1m3k0.modbus.core.request.UndefinedRequest;

public class UndefinedFunction extends ModbusFunction<UndefinedFunction, UndefinedRequest, UndefinedResponse, UndefinedError> {
    private UndefinedFunction() {
        super(FunctionCode.Undefined, FunctionErrorCode.Undefined);
    }

    public static UndefinedFunction Instance() {
        return new UndefinedFunction();
    }
}
