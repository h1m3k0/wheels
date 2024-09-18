package com.github.h1m3k0.modbus.core.request;

import com.github.h1m3k0.modbus.core.errorres.ReadDiscreteInputsError;
import com.github.h1m3k0.modbus.core.response.ReadDiscreteInputsResponse;
import com.github.h1m3k0.modbus.core.function.ReadDiscreteInputsFunction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class ReadDiscreteInputsRequest extends ModbusRequest<ReadDiscreteInputsFunction, ReadDiscreteInputsRequest, ReadDiscreteInputsResponse, ReadDiscreteInputsError> {
    private final int address;
    private final int quantity;

    public ReadDiscreteInputsRequest(int address, int quantity) {
        super.function = ReadDiscreteInputsFunction.Instance();
        this.address = address;
        this.quantity = quantity;
    }
}
