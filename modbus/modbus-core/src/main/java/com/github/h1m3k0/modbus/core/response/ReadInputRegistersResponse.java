package com.github.h1m3k0.modbus.core.response;

import com.github.h1m3k0.modbus.core.errorres.ReadInputRegistersError;
import com.github.h1m3k0.modbus.core.function.ReadInputRegistersFunction;
import com.github.h1m3k0.modbus.core.request.ReadInputRegistersRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class ReadInputRegistersResponse extends ModbusResponse<ReadInputRegistersFunction, ReadInputRegistersRequest, ReadInputRegistersResponse, ReadInputRegistersError> {
    private final byte[] value;

    public ReadInputRegistersResponse(byte[] value) {
        super.function = ReadInputRegistersFunction.Instance();
        this.value = value;
    }
}
