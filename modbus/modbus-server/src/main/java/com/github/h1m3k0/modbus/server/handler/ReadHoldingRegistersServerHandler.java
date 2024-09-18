package com.github.h1m3k0.modbus.server.handler;

import com.github.h1m3k0.modbus.core.errorres.ReadHoldingRegistersError;
import com.github.h1m3k0.modbus.core.function.ReadHoldingRegistersFunction;
import com.github.h1m3k0.modbus.core.request.ReadHoldingRegistersRequest;
import com.github.h1m3k0.modbus.core.response.ReadHoldingRegistersResponse;
import com.github.h1m3k0.modbus.server.functional.ModbusFunctional;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReadHoldingRegistersServerHandler extends MessageServerHandler<ReadHoldingRegistersFunction, ReadHoldingRegistersRequest, ReadHoldingRegistersResponse, ReadHoldingRegistersError> {
    public ReadHoldingRegistersServerHandler(ModbusFunctional<ReadHoldingRegistersFunction, ReadHoldingRegistersRequest, ReadHoldingRegistersResponse, ReadHoldingRegistersError> function) {
        super(function);
    }
}
