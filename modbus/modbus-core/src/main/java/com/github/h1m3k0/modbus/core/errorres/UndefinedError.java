package com.github.h1m3k0.modbus.core.errorres;

import com.github.h1m3k0.modbus.core.enums.ExceptionCode;
import com.github.h1m3k0.modbus.core.function.UndefinedFunction;
import com.github.h1m3k0.modbus.core.request.UndefinedRequest;
import com.github.h1m3k0.modbus.core.response.UndefinedResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true, fluent = true)
public class UndefinedError extends ModbusError<UndefinedFunction, UndefinedRequest, UndefinedResponse, UndefinedError> {
    /**
     * 功能码, 自定义
     */
    private byte code;

    public UndefinedError(ExceptionCode exceptionCode, byte code) {
        super(exceptionCode);
        this.code = (byte) (code + (code >= 0 ? 0x80 : 0));
    }

}
