package com.github.h1m3k0.modbus.core.request;

import com.github.h1m3k0.modbus.core.errorres.ReadCoilsError;
import com.github.h1m3k0.modbus.core.function.ReadCoilsFunction;
import com.github.h1m3k0.modbus.core.response.ReadCoilsResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class ReadCoilsRequest extends ModbusRequest<ReadCoilsFunction, ReadCoilsRequest, ReadCoilsResponse, ReadCoilsError> {
    private final int address;
    private final int quantity;

    public ReadCoilsRequest(int address, int quantity) {
        super.function = ReadCoilsFunction.Instance();
        this.address = address;
        this.quantity = quantity;
    }
}
