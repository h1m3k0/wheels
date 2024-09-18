package com.github.h1m3k0.modbus.core.function;

import com.github.h1m3k0.modbus.core.enums.FunctionErrorCode;
import com.github.h1m3k0.modbus.core.errorres.ReadDiscreteInputsError;
import com.github.h1m3k0.modbus.core.request.ReadDiscreteInputsRequest;
import com.github.h1m3k0.modbus.core.response.ReadDiscreteInputsResponse;
import com.github.h1m3k0.modbus.core.enums.FunctionCode;

public class ReadDiscreteInputsFunction extends ModbusFunction<ReadDiscreteInputsFunction, ReadDiscreteInputsRequest, ReadDiscreteInputsResponse, ReadDiscreteInputsError> {
    private ReadDiscreteInputsFunction() {
        super(
                FunctionCode.ReadDiscreteInputs,
                FunctionErrorCode.ReadDiscreteInputs
        );
    }

    private static final ReadDiscreteInputsFunction Instance = new ReadDiscreteInputsFunction();

    public static ReadDiscreteInputsFunction Instance() {
        return Instance;
    }
}
