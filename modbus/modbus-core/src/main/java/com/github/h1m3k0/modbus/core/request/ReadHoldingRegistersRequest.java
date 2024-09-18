package com.github.h1m3k0.modbus.core.request;

import com.github.h1m3k0.modbus.core.function.ReadHoldingRegistersFunction;
import com.github.h1m3k0.modbus.core.errorres.ReadHoldingRegistersError;
import com.github.h1m3k0.modbus.core.response.ReadHoldingRegistersResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class ReadHoldingRegistersRequest extends ModbusRequest<ReadHoldingRegistersFunction, ReadHoldingRegistersRequest, ReadHoldingRegistersResponse, ReadHoldingRegistersError> {
    private final int address;
    private final int quantity;

    public ReadHoldingRegistersRequest(int address, int quantity) {
        super.function = ReadHoldingRegistersFunction.Instance();
        this.address = address;
        this.quantity = quantity;
    }
}
