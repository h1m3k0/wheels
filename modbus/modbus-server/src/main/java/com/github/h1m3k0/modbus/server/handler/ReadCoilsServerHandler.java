package com.github.h1m3k0.modbus.server.handler;

import com.github.h1m3k0.modbus.core.errorres.ReadCoilsError;
import com.github.h1m3k0.modbus.core.function.ReadCoilsFunction;
import com.github.h1m3k0.modbus.core.request.ReadCoilsRequest;
import com.github.h1m3k0.modbus.core.response.ReadCoilsResponse;
import com.github.h1m3k0.modbus.server.functional.ModbusFunctional;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReadCoilsServerHandler extends MessageServerHandler<ReadCoilsFunction, ReadCoilsRequest, ReadCoilsResponse, ReadCoilsError> {
    public ReadCoilsServerHandler(ModbusFunctional<ReadCoilsFunction, ReadCoilsRequest, ReadCoilsResponse, ReadCoilsError> function) {
        super(function);
    }
}
