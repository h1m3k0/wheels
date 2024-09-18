package com.github.h1m3k0.modbus.core.request;

import com.github.h1m3k0.modbus.core.errorres.ReadWriteMultipleRegistersError;
import com.github.h1m3k0.modbus.core.function.ReadWriteMultipleRegistersFunction;
import com.github.h1m3k0.modbus.core.response.ReadWriteMultipleRegistersResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class ReadWriteMultipleRegistersRequest extends ModbusRequest<ReadWriteMultipleRegistersFunction, ReadWriteMultipleRegistersRequest, ReadWriteMultipleRegistersResponse, ReadWriteMultipleRegistersError> {
    private final int readAddress;
    private final int readQuantity;
    private final int writeAddress;
    private final int writeQuantity;  // 用于校验
    private final byte[] writeValue;

    public ReadWriteMultipleRegistersRequest(int readAddress, int readQuantity, int writeAddress, byte[] writeValue) {
        super.function = ReadWriteMultipleRegistersFunction.Instance();
        this.readAddress = readAddress;
        this.readQuantity = readQuantity;
        this.writeAddress = writeAddress;
        this.writeQuantity = writeValue.length >>> 1;
        this.writeValue = writeValue;
    }
}
