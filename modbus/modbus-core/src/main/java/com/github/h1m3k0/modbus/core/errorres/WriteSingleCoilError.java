package com.github.h1m3k0.modbus.core.errorres;

import com.github.h1m3k0.modbus.core.enums.ExceptionCode;
import com.github.h1m3k0.modbus.core.response.WriteSingleCoilResponse;
import com.github.h1m3k0.modbus.core.function.WriteSingleCoilFunction;
import com.github.h1m3k0.modbus.core.request.WriteSingleCoilRequest;

public class WriteSingleCoilError extends ModbusError<WriteSingleCoilFunction, WriteSingleCoilRequest, WriteSingleCoilResponse, WriteSingleCoilError> {
    public WriteSingleCoilError(ExceptionCode exceptionCode) {
        super(exceptionCode);
        super.function = WriteSingleCoilFunction.Instance();
    }
}
