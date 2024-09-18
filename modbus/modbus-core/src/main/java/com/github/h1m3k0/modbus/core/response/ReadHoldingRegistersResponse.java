package com.github.h1m3k0.modbus.core.response;

import com.github.h1m3k0.modbus.core.function.ReadHoldingRegistersFunction;
import com.github.h1m3k0.modbus.core.request.ReadHoldingRegistersRequest;
import com.github.h1m3k0.modbus.core.errorres.ReadHoldingRegistersError;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class ReadHoldingRegistersResponse extends ModbusResponse<ReadHoldingRegistersFunction, ReadHoldingRegistersRequest, ReadHoldingRegistersResponse, ReadHoldingRegistersError> {
    private final byte[] value;

    public ReadHoldingRegistersResponse(byte[] value) {
        super.function = ReadHoldingRegistersFunction.Instance();
        this.value = value;
    }
}
