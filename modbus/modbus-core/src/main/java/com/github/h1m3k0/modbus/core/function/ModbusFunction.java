package com.github.h1m3k0.modbus.core.function;

import com.github.h1m3k0.modbus.core.enums.FunctionCode;
import com.github.h1m3k0.modbus.core.enums.FunctionErrorCode;
import com.github.h1m3k0.modbus.core.errorres.ModbusError;
import com.github.h1m3k0.modbus.core.request.ModbusRequest;
import com.github.h1m3k0.modbus.core.response.ModbusResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true, fluent = true)
@AllArgsConstructor
public abstract class ModbusFunction<
        function extends ModbusFunction<function, request, response, error>,
        request extends ModbusRequest<function, request, response, error>,
        response extends ModbusResponse<function, request, response, error>,
        error extends ModbusError<function, request, response, error>> {
    /**
     * 正常的功能码
     */
    protected final FunctionCode code;
    /**
     * 失败的功能码
     */
    protected final FunctionErrorCode errorCode;
}
