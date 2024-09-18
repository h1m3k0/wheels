package com.github.h1m3k0.modbus.core.response;

import com.github.h1m3k0.modbus.core.errorres.WriteMultipleCoilsError;
import com.github.h1m3k0.modbus.core.function.WriteMultipleCoilsFunction;
import com.github.h1m3k0.modbus.core.request.WriteMultipleCoilsRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class WriteMultipleCoilsResponse extends ModbusResponse<WriteMultipleCoilsFunction, WriteMultipleCoilsRequest, WriteMultipleCoilsResponse, WriteMultipleCoilsError> {
    private final int address;
    private final int quantity;

    public WriteMultipleCoilsResponse(int address, int quantity) {
        super.function = WriteMultipleCoilsFunction.Instance();
        this.address = address;
        this.quantity = quantity;
    }
}
