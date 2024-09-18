package com.github.h1m3k0.modbus.server.functional;

import com.github.h1m3k0.modbus.core.ModbusException;
import com.github.h1m3k0.modbus.core.errorres.ModbusError;
import com.github.h1m3k0.modbus.core.function.ModbusFunction;
import com.github.h1m3k0.modbus.core.request.ModbusRequest;
import com.github.h1m3k0.modbus.core.response.ModbusResponse;

@FunctionalInterface
public interface ModbusFunctional<
        function extends ModbusFunction<function, request, response, error>,
        request extends ModbusRequest<function, request, response, error>,
        response extends ModbusResponse<function, request, response, error>,
        error extends ModbusError<function, request, response, error>> {
    response apply(request t) throws ModbusException;
}

