package com.github.h1m3k0.modbus.core.response;

import com.github.h1m3k0.modbus.core.enums.SingleCoil;
import com.github.h1m3k0.modbus.core.errorres.WriteSingleCoilError;
import com.github.h1m3k0.modbus.core.function.WriteSingleCoilFunction;
import com.github.h1m3k0.modbus.core.request.WriteSingleCoilRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class WriteSingleCoilResponse extends ModbusResponse<WriteSingleCoilFunction, WriteSingleCoilRequest, WriteSingleCoilResponse, WriteSingleCoilError> {
    private final int address;
    private final SingleCoil value;

    public WriteSingleCoilResponse(int address, boolean data) {
        this(address, SingleCoil.get(data));
    }

    public WriteSingleCoilResponse(int address, SingleCoil value) {
        super.function = WriteSingleCoilFunction.Instance();
        this.address = address;
        this.value = value;
    }
}
