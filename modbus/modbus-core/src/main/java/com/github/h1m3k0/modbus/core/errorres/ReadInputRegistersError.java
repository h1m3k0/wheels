package com.github.h1m3k0.modbus.core.errorres;

import com.github.h1m3k0.modbus.core.enums.ExceptionCode;
import com.github.h1m3k0.modbus.core.response.ReadInputRegistersResponse;
import com.github.h1m3k0.modbus.core.function.ReadInputRegistersFunction;
import com.github.h1m3k0.modbus.core.request.ReadInputRegistersRequest;

public class ReadInputRegistersError extends ModbusError<ReadInputRegistersFunction, ReadInputRegistersRequest, ReadInputRegistersResponse, ReadInputRegistersError> {
    public ReadInputRegistersError(ExceptionCode exceptionCode) {
        super(exceptionCode);
        super.function = ReadInputRegistersFunction.Instance();
    }
}
