package com.github.h1m3k0.modbus.server.handler;

import com.github.h1m3k0.modbus.core.errorres.ReadDiscreteInputsError;
import com.github.h1m3k0.modbus.core.function.ReadDiscreteInputsFunction;
import com.github.h1m3k0.modbus.core.request.ReadDiscreteInputsRequest;
import com.github.h1m3k0.modbus.core.response.ReadDiscreteInputsResponse;
import com.github.h1m3k0.modbus.server.functional.ModbusFunctional;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReadDiscreteInputsServerHandler extends MessageServerHandler<ReadDiscreteInputsFunction, ReadDiscreteInputsRequest, ReadDiscreteInputsResponse, ReadDiscreteInputsError> {
    public ReadDiscreteInputsServerHandler(ModbusFunctional<ReadDiscreteInputsFunction, ReadDiscreteInputsRequest, ReadDiscreteInputsResponse, ReadDiscreteInputsError> function) {
        super(function);
    }
}
