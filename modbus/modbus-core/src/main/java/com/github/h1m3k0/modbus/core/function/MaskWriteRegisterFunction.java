package com.github.h1m3k0.modbus.core.function;

import com.github.h1m3k0.modbus.core.enums.FunctionErrorCode;
import com.github.h1m3k0.modbus.core.errorres.MaskWriteRegisterError;
import com.github.h1m3k0.modbus.core.enums.FunctionCode;
import com.github.h1m3k0.modbus.core.request.MaskWriteRegisterRequest;
import com.github.h1m3k0.modbus.core.response.MaskWriteRegisterResponse;

public class MaskWriteRegisterFunction extends ModbusFunction<MaskWriteRegisterFunction, MaskWriteRegisterRequest, MaskWriteRegisterResponse, MaskWriteRegisterError> {
    private MaskWriteRegisterFunction() {
        super(
                FunctionCode.MaskWriteRegister,
                FunctionErrorCode.MaskWriteRegister
        );
    }

    private static final MaskWriteRegisterFunction Instance = new MaskWriteRegisterFunction();

    public static MaskWriteRegisterFunction Instance() {
        return Instance;
    }
}
