package com.github.h1m3k0.modbus.core.request;

import com.github.h1m3k0.modbus.core.errorres.WriteMultipleRegistersError;
import com.github.h1m3k0.modbus.core.function.WriteMultipleRegistersFunction;
import com.github.h1m3k0.modbus.core.response.WriteMultipleRegistersResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class WriteMultipleRegistersRequest extends ModbusRequest<WriteMultipleRegistersFunction, WriteMultipleRegistersRequest, WriteMultipleRegistersResponse, WriteMultipleRegistersError> {
    private final int address;
    private final int quantity;  // 用于校验
    private final byte[] value;

    public WriteMultipleRegistersRequest(int address, byte[] value) {
        super.function = WriteMultipleRegistersFunction.Instance();
        this.address = address;
        this.quantity = value.length >>> 1;
        this.value = value;
    }
}
