package com.github.h1m3k0.modbus.server.handler;

import com.github.h1m3k0.modbus.core.ModbusConsumer;
import com.github.h1m3k0.modbus.core.errorres.WriteMultipleRegistersError;
import com.github.h1m3k0.modbus.core.function.WriteMultipleRegistersFunction;
import com.github.h1m3k0.modbus.core.request.WriteMultipleRegistersRequest;
import com.github.h1m3k0.modbus.core.response.WriteMultipleRegistersResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WriteMultipleRegistersServerHandler extends MessageServerHandler<WriteMultipleRegistersFunction, WriteMultipleRegistersRequest, WriteMultipleRegistersResponse, WriteMultipleRegistersError> {
    public WriteMultipleRegistersServerHandler(ModbusConsumer<WriteMultipleRegistersRequest> consumer) {
        super(request -> {
            WriteMultipleRegistersResponse response = new WriteMultipleRegistersResponse(request.address(), request.value().length >>> 1);
            if (consumer != null) {
                consumer.accept(request);
            }
            return response;
        });
    }
}
