package com.github.h1m3k0.modbus.core.request;

import com.github.h1m3k0.modbus.core.errorres.ReadInputRegistersError;
import com.github.h1m3k0.modbus.core.function.ReadInputRegistersFunction;
import com.github.h1m3k0.modbus.core.response.ReadInputRegistersResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class ReadInputRegistersRequest extends ModbusRequest<ReadInputRegistersFunction, ReadInputRegistersRequest, ReadInputRegistersResponse, ReadInputRegistersError> {
    private final int address;
    private final int quantity;

    public ReadInputRegistersRequest(int address, int quantity) {
        super.function = ReadInputRegistersFunction.Instance();
        this.address = address;
        this.quantity = quantity;
    }
}
