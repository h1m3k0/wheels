package com.github.h1m3k0.modbus.core;

import com.github.h1m3k0.modbus.core.enums.ExceptionCause;
import com.github.h1m3k0.modbus.core.errorres.ModbusError;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class ModbusException extends Exception {
    private final ExceptionCause reason;
    private ModbusError<?, ?, ?, ?> errorMessage;

    public ModbusException(ExceptionCause reason) {
        this.reason = reason;
    }

    public ModbusException(ExceptionCause reason, ModbusError<?, ?, ?, ?> errorMessage) {
        this.reason = reason;
        this.errorMessage = errorMessage;
    }
}
