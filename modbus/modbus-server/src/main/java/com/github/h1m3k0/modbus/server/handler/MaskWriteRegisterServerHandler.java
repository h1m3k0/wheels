package com.github.h1m3k0.modbus.server.handler;

import com.github.h1m3k0.modbus.core.ModbusConsumer;
import com.github.h1m3k0.modbus.core.errorres.MaskWriteRegisterError;
import com.github.h1m3k0.modbus.core.function.MaskWriteRegisterFunction;
import com.github.h1m3k0.modbus.core.request.MaskWriteRegisterRequest;
import com.github.h1m3k0.modbus.core.response.MaskWriteRegisterResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MaskWriteRegisterServerHandler extends MessageServerHandler<MaskWriteRegisterFunction, MaskWriteRegisterRequest, MaskWriteRegisterResponse, MaskWriteRegisterError> {
    public MaskWriteRegisterServerHandler(ModbusConsumer<MaskWriteRegisterRequest> consumer) {
        super(request -> {
            MaskWriteRegisterResponse response = new MaskWriteRegisterResponse(request.address(), request.andMask(), request.orMask());
            if (consumer != null) {
                consumer.accept(request);
            }
            return response;
        });
    }
}
