package com.github.h1m3k0.modbus.core.errorres;

import com.github.h1m3k0.modbus.core.enums.ExceptionCode;
import com.github.h1m3k0.modbus.core.function.ReadWriteMultipleRegistersFunction;
import com.github.h1m3k0.modbus.core.request.ReadWriteMultipleRegistersRequest;
import com.github.h1m3k0.modbus.core.response.ReadWriteMultipleRegistersResponse;

public class ReadWriteMultipleRegistersError extends ModbusError<ReadWriteMultipleRegistersFunction, ReadWriteMultipleRegistersRequest, ReadWriteMultipleRegistersResponse, ReadWriteMultipleRegistersError> {
    public ReadWriteMultipleRegistersError(ExceptionCode exceptionCode) {
        super(exceptionCode);
        super.function = ReadWriteMultipleRegistersFunction.Instance();
    }
}
