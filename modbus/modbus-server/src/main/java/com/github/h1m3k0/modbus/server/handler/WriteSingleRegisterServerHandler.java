package com.github.h1m3k0.modbus.server.handler;

import com.github.h1m3k0.modbus.core.ModbusConsumer;
import com.github.h1m3k0.modbus.core.errorres.WriteSingleRegisterError;
import com.github.h1m3k0.modbus.core.function.WriteSingleRegisterFunction;
import com.github.h1m3k0.modbus.core.request.WriteSingleRegisterRequest;
import com.github.h1m3k0.modbus.core.response.WriteSingleRegisterResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WriteSingleRegisterServerHandler extends MessageServerHandler<WriteSingleRegisterFunction, WriteSingleRegisterRequest, WriteSingleRegisterResponse, WriteSingleRegisterError> {
    public WriteSingleRegisterServerHandler(ModbusConsumer<WriteSingleRegisterRequest> consumer) {
        super(request -> {
            WriteSingleRegisterResponse response = new WriteSingleRegisterResponse(request.address(), request.value());
            if (consumer != null) {
                consumer.accept(request);
            }
            return response;
        });
    }
}
