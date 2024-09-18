package com.github.h1m3k0.modbus.server.handler;

import com.github.h1m3k0.modbus.core.ModbusConsumer;
import com.github.h1m3k0.modbus.core.errorres.WriteSingleCoilError;
import com.github.h1m3k0.modbus.core.function.WriteSingleCoilFunction;
import com.github.h1m3k0.modbus.core.request.WriteSingleCoilRequest;
import com.github.h1m3k0.modbus.core.response.WriteSingleCoilResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WriteSingleCoilServerHandler extends MessageServerHandler<WriteSingleCoilFunction, WriteSingleCoilRequest, WriteSingleCoilResponse, WriteSingleCoilError> {
    public WriteSingleCoilServerHandler(ModbusConsumer<WriteSingleCoilRequest> consumer) {
        super(request -> {
            WriteSingleCoilResponse response = new WriteSingleCoilResponse(request.address(), request.value().boolValue());
            if (consumer != null) {
                consumer.accept(request);
            }
            return response;
        });
    }
}
