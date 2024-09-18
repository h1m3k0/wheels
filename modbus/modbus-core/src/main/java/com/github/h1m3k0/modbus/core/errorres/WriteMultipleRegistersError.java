package com.github.h1m3k0.modbus.core.errorres;

import com.github.h1m3k0.modbus.core.enums.ExceptionCode;
import com.github.h1m3k0.modbus.core.function.WriteMultipleRegistersFunction;
import com.github.h1m3k0.modbus.core.request.WriteMultipleRegistersRequest;
import com.github.h1m3k0.modbus.core.response.WriteMultipleRegistersResponse;

public class WriteMultipleRegistersError extends ModbusError<WriteMultipleRegistersFunction, WriteMultipleRegistersRequest, WriteMultipleRegistersResponse, WriteMultipleRegistersError> {
    public WriteMultipleRegistersError(ExceptionCode exceptionCode) {
        super(exceptionCode);
        super.function = WriteMultipleRegistersFunction.Instance();
    }
}
