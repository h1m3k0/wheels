package com.github.h1m3k0.modbus.core.errorres;

import com.github.h1m3k0.modbus.core.enums.ExceptionCode;
import com.github.h1m3k0.modbus.core.function.MaskWriteRegisterFunction;
import com.github.h1m3k0.modbus.core.request.MaskWriteRegisterRequest;
import com.github.h1m3k0.modbus.core.response.MaskWriteRegisterResponse;

public class MaskWriteRegisterError extends ModbusError<MaskWriteRegisterFunction, MaskWriteRegisterRequest, MaskWriteRegisterResponse, MaskWriteRegisterError> {
    public MaskWriteRegisterError(ExceptionCode exceptionCode) {
        super(exceptionCode);
        super.function = MaskWriteRegisterFunction.Instance();
    }
}
