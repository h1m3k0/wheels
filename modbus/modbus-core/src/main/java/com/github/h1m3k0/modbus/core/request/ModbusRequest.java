package com.github.h1m3k0.modbus.core.request;

import com.github.h1m3k0.modbus.core.ModbusMessage;
import com.github.h1m3k0.modbus.core.function.ModbusFunction;
import com.github.h1m3k0.modbus.core.errorres.ModbusError;
import com.github.h1m3k0.modbus.core.response.ModbusResponse;

public abstract class ModbusRequest<
        function extends ModbusFunction<function, request, response, error>,
        request extends ModbusRequest<function, request, response, error>,
        response extends ModbusResponse<function, request, response, error>,
        error extends ModbusError<function, request, response, error>
        > extends ModbusMessage<request, function, request, response, error> {
    /**
     * 功能码, 来自code
     */
    @Override
    public final byte code() {
        return super.function.code().value();
    }
}
