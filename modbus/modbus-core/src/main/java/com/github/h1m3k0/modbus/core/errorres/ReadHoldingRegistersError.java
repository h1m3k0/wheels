package com.github.h1m3k0.modbus.core.errorres;

import com.github.h1m3k0.modbus.core.enums.ExceptionCode;
import com.github.h1m3k0.modbus.core.function.ReadHoldingRegistersFunction;
import com.github.h1m3k0.modbus.core.request.ReadHoldingRegistersRequest;
import com.github.h1m3k0.modbus.core.response.ReadHoldingRegistersResponse;

public class ReadHoldingRegistersError extends ModbusError<ReadHoldingRegistersFunction, ReadHoldingRegistersRequest, ReadHoldingRegistersResponse, ReadHoldingRegistersError> {
    public ReadHoldingRegistersError(ExceptionCode exceptionCode) {
        super(exceptionCode);
        super.function = ReadHoldingRegistersFunction.Instance();
    }
}
