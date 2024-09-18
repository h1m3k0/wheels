package com.github.h1m3k0.modbus.core.response;

import com.github.h1m3k0.modbus.core.errorres.WriteMultipleRegistersError;
import com.github.h1m3k0.modbus.core.function.WriteMultipleRegistersFunction;
import com.github.h1m3k0.modbus.core.request.WriteMultipleRegistersRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class WriteMultipleRegistersResponse extends ModbusResponse<WriteMultipleRegistersFunction, WriteMultipleRegistersRequest, WriteMultipleRegistersResponse, WriteMultipleRegistersError> {
    private final int address;
    private final int quantity;

    public WriteMultipleRegistersResponse(int address, int quantity) {
        super.function = WriteMultipleRegistersFunction.Instance();
        this.address = address;
        this.quantity = quantity;
    }
}
