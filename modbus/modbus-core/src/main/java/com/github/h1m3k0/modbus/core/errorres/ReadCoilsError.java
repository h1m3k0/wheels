package com.github.h1m3k0.modbus.core.errorres;

import com.github.h1m3k0.modbus.core.enums.ExceptionCode;
import com.github.h1m3k0.modbus.core.request.ReadCoilsRequest;
import com.github.h1m3k0.modbus.core.function.ReadCoilsFunction;
import com.github.h1m3k0.modbus.core.response.ReadCoilsResponse;

public class ReadCoilsError extends ModbusError<ReadCoilsFunction, ReadCoilsRequest, ReadCoilsResponse, ReadCoilsError> {
    public ReadCoilsError(ExceptionCode exceptionCode) {
        super(exceptionCode);
        super.function = ReadCoilsFunction.Instance();
    }
}
