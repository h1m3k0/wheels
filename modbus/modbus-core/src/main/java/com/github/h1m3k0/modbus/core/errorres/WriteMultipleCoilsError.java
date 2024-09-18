package com.github.h1m3k0.modbus.core.errorres;

import com.github.h1m3k0.modbus.core.enums.ExceptionCode;
import com.github.h1m3k0.modbus.core.function.WriteMultipleCoilsFunction;
import com.github.h1m3k0.modbus.core.request.WriteMultipleCoilsRequest;
import com.github.h1m3k0.modbus.core.response.WriteMultipleCoilsResponse;

public class WriteMultipleCoilsError extends ModbusError<WriteMultipleCoilsFunction, WriteMultipleCoilsRequest, WriteMultipleCoilsResponse, WriteMultipleCoilsError> {
    public WriteMultipleCoilsError(ExceptionCode exceptionCode) {
        super(exceptionCode);
        super.function = WriteMultipleCoilsFunction.Instance();
    }
}
