package com.github.h1m3k0.modbus.core.response;

import com.github.h1m3k0.modbus.core.errorres.MaskWriteRegisterError;
import com.github.h1m3k0.modbus.core.function.MaskWriteRegisterFunction;
import com.github.h1m3k0.modbus.core.request.MaskWriteRegisterRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class MaskWriteRegisterResponse extends ModbusResponse<MaskWriteRegisterFunction, MaskWriteRegisterRequest, MaskWriteRegisterResponse, MaskWriteRegisterError> {
    private final int address;
    private final short andMask;
    private final short orMask;

    public MaskWriteRegisterResponse(int address, short andMask, short orMask) {
        super.function = MaskWriteRegisterFunction.Instance();
        this.address = address;
        this.andMask = andMask;
        this.orMask = orMask;
    }
}
