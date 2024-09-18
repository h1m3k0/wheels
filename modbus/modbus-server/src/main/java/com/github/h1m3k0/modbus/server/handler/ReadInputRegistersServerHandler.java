package com.github.h1m3k0.modbus.server.handler;

import com.github.h1m3k0.modbus.core.errorres.ReadInputRegistersError;
import com.github.h1m3k0.modbus.core.function.ReadInputRegistersFunction;
import com.github.h1m3k0.modbus.core.request.ReadInputRegistersRequest;
import com.github.h1m3k0.modbus.core.response.ReadInputRegistersResponse;
import com.github.h1m3k0.modbus.server.functional.ModbusFunctional;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReadInputRegistersServerHandler extends MessageServerHandler<ReadInputRegistersFunction, ReadInputRegistersRequest, ReadInputRegistersResponse, ReadInputRegistersError> {
    public ReadInputRegistersServerHandler(ModbusFunctional<ReadInputRegistersFunction, ReadInputRegistersRequest, ReadInputRegistersResponse, ReadInputRegistersError> function) {
        super(function);
    }
}
