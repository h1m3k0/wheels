package com.github.h1m3k0.modbus.core.errorres;

import com.github.h1m3k0.modbus.core.enums.ExceptionCode;
import com.github.h1m3k0.modbus.core.response.ReadDiscreteInputsResponse;
import com.github.h1m3k0.modbus.core.function.ReadDiscreteInputsFunction;
import com.github.h1m3k0.modbus.core.request.ReadDiscreteInputsRequest;

public class ReadDiscreteInputsError extends ModbusError<ReadDiscreteInputsFunction, ReadDiscreteInputsRequest, ReadDiscreteInputsResponse, ReadDiscreteInputsError> {
    public ReadDiscreteInputsError(ExceptionCode exceptionCode) {
        super(exceptionCode);
        super.function = ReadDiscreteInputsFunction.Instance();
    }
}
