package com.github.h1m3k0.modbus.core.response;

import com.github.h1m3k0.modbus.core.errorres.WriteSingleRegisterError;
import com.github.h1m3k0.modbus.core.function.WriteSingleRegisterFunction;
import com.github.h1m3k0.modbus.core.request.WriteSingleRegisterRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class WriteSingleRegisterResponse extends ModbusResponse<WriteSingleRegisterFunction, WriteSingleRegisterRequest, WriteSingleRegisterResponse, WriteSingleRegisterError> {
    private final int address;
    private final short data;

    public WriteSingleRegisterResponse(int address, short data) {
        super.function = WriteSingleRegisterFunction.Instance();
        this.address = address;
        this.data = data;
    }
}
