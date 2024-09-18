package com.github.h1m3k0.modbus.core.errorres;

import com.github.h1m3k0.modbus.core.ModbusMessage;
import com.github.h1m3k0.modbus.core.enums.ExceptionCode;
import com.github.h1m3k0.modbus.core.function.ModbusFunction;
import com.github.h1m3k0.modbus.core.request.ModbusRequest;
import com.github.h1m3k0.modbus.core.response.ModbusResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
@AllArgsConstructor
public abstract class ModbusError<
        function extends ModbusFunction<function, request, response, error>,
        request extends ModbusRequest<function, request, response, error>,
        response extends ModbusResponse<function, request, response, error>,
        error extends ModbusError<function, request, response, error>
        > extends ModbusMessage<error, function, request, response, error> {
    protected final ExceptionCode exceptionCode;

    /**
     * 功能码, 来自errorCode
     */
    @Override
    public byte code() {
        return super.function.errorCode().value();
    }
}
