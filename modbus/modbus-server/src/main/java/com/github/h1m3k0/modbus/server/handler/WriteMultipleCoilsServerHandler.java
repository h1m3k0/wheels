package com.github.h1m3k0.modbus.server.handler;

import com.github.h1m3k0.modbus.core.ModbusConsumer;
import com.github.h1m3k0.modbus.core.errorres.WriteMultipleCoilsError;
import com.github.h1m3k0.modbus.core.function.WriteMultipleCoilsFunction;
import com.github.h1m3k0.modbus.core.request.WriteMultipleCoilsRequest;
import com.github.h1m3k0.modbus.core.response.WriteMultipleCoilsResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WriteMultipleCoilsServerHandler extends MessageServerHandler<WriteMultipleCoilsFunction, WriteMultipleCoilsRequest, WriteMultipleCoilsResponse, WriteMultipleCoilsError> {
    public WriteMultipleCoilsServerHandler(ModbusConsumer<WriteMultipleCoilsRequest> consumer) {
        super(request -> {
            WriteMultipleCoilsResponse response = new WriteMultipleCoilsResponse(request.address(), request.quantity());
            if (consumer != null) {
                consumer.accept(request);
            }
            return response;
        });
    }
}
