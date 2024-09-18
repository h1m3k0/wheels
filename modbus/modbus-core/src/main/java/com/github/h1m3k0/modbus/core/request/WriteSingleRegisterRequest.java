package com.github.h1m3k0.modbus.core.request;

import com.github.h1m3k0.modbus.core.errorres.WriteSingleRegisterError;
import com.github.h1m3k0.modbus.core.function.WriteSingleRegisterFunction;
import com.github.h1m3k0.modbus.core.response.WriteSingleRegisterResponse;
import com.github.h1m3k0.modbus.core.utils.ModbusUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class WriteSingleRegisterRequest extends ModbusRequest<WriteSingleRegisterFunction, WriteSingleRegisterRequest, WriteSingleRegisterResponse, WriteSingleRegisterError> {
    private final int address;
    private final short value;

    public WriteSingleRegisterRequest(int address, short value) {
        super.function = WriteSingleRegisterFunction.Instance();
        this.address = address;
        this.value = value;
    }

    public WriteSingleRegisterRequest(int address, byte[] bytes) {
        this(address, ModbusUtil.bytesToShort(bytes));
    }
}
