package com.github.h1m3k0.modbus.core;

import com.github.h1m3k0.modbus.core.enums.ExceptionCause;
import com.github.h1m3k0.modbus.core.errorres.ModbusError;
import com.github.h1m3k0.modbus.core.request.ModbusRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class ModbusException extends Exception {
    private final ExceptionCause reason;
    private ModbusError<?, ?, ?, ?> message;
    private ModbusRequest<?, ?, ?, ?> request;

    public ModbusException(ExceptionCause reason) {
        this.reason = reason;
    }

    public ModbusException(ModbusError<?, ?, ?, ?> message) {
        this.reason = ExceptionCause.ErrorMessage;
        this.message = message;
    }

    public ModbusException(ExceptionCause reason, ModbusRequest<?, ?, ?, ?> request) {
        this.reason = reason;
        this.request = request;
    }
}
