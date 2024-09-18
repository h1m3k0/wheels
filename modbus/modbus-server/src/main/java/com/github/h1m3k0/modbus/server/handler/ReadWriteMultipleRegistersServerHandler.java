package com.github.h1m3k0.modbus.server.handler;

import com.github.h1m3k0.modbus.core.errorres.ReadWriteMultipleRegistersError;
import com.github.h1m3k0.modbus.core.function.ReadWriteMultipleRegistersFunction;
import com.github.h1m3k0.modbus.core.request.ReadWriteMultipleRegistersRequest;
import com.github.h1m3k0.modbus.core.response.ReadWriteMultipleRegistersResponse;
import com.github.h1m3k0.modbus.server.functional.ModbusFunctional;

public class ReadWriteMultipleRegistersServerHandler extends MessageServerHandler<ReadWriteMultipleRegistersFunction, ReadWriteMultipleRegistersRequest, ReadWriteMultipleRegistersResponse, ReadWriteMultipleRegistersError> {
    public ReadWriteMultipleRegistersServerHandler(ModbusFunctional<ReadWriteMultipleRegistersFunction, ReadWriteMultipleRegistersRequest, ReadWriteMultipleRegistersResponse, ReadWriteMultipleRegistersError> function) {
        super(function);
    }
}
