package com.github.h1m3k0.modbus.core.request;

import com.github.h1m3k0.modbus.core.errorres.WriteSingleCoilError;
import com.github.h1m3k0.modbus.core.response.WriteSingleCoilResponse;
import com.github.h1m3k0.modbus.core.enums.SingleCoil;
import com.github.h1m3k0.modbus.core.function.WriteSingleCoilFunction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class WriteSingleCoilRequest extends ModbusRequest<WriteSingleCoilFunction, WriteSingleCoilRequest, WriteSingleCoilResponse, WriteSingleCoilError> {
    private final int address;
    private final SingleCoil value;

    public WriteSingleCoilRequest(int address, boolean data) {
        this(address, SingleCoil.get(data));
    }

    public WriteSingleCoilRequest(int address, SingleCoil value) {
        super.function = WriteSingleCoilFunction.Instance();
        this.address = address;
        this.value = value;
    }
}
