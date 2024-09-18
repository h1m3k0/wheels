package com.github.h1m3k0.modbus.core.response;

import com.github.h1m3k0.modbus.core.errorres.ReadWriteMultipleRegistersError;
import com.github.h1m3k0.modbus.core.function.ReadWriteMultipleRegistersFunction;
import com.github.h1m3k0.modbus.core.request.ReadWriteMultipleRegistersRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class ReadWriteMultipleRegistersResponse extends ModbusResponse<ReadWriteMultipleRegistersFunction, ReadWriteMultipleRegistersRequest, ReadWriteMultipleRegistersResponse, ReadWriteMultipleRegistersError> {
    private final byte[] readValue;

    public ReadWriteMultipleRegistersResponse(byte[] readValue) {
        super.function = ReadWriteMultipleRegistersFunction.Instance();
        this.readValue = readValue;
    }
}
