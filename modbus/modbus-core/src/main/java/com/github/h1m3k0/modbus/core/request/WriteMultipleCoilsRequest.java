package com.github.h1m3k0.modbus.core.request;

import com.github.h1m3k0.modbus.core.errorres.WriteMultipleCoilsError;
import com.github.h1m3k0.modbus.core.function.WriteMultipleCoilsFunction;
import com.github.h1m3k0.modbus.core.response.WriteMultipleCoilsResponse;
import com.github.h1m3k0.modbus.core.utils.ModbusUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class WriteMultipleCoilsRequest extends ModbusRequest<WriteMultipleCoilsFunction, WriteMultipleCoilsRequest, WriteMultipleCoilsResponse, WriteMultipleCoilsError> {
    private final int address;
    private final int quantity;
    private final byte[] bytes;
    private final boolean[] bits;

    private WriteMultipleCoilsRequest(int address, int quantity, byte[] bytes, boolean[] bits) {
        super.function = WriteMultipleCoilsFunction.Instance();
        this.address = address;
        this.quantity = quantity;
        this.bytes = bytes;
        this.bits = bits;
    }

    public WriteMultipleCoilsRequest(int address, int quantity, byte[] bytes) {
        this(address, quantity, bytes, ModbusUtil.bytesToBits(bytes, quantity));
    }

    public WriteMultipleCoilsRequest(int address, boolean[] bits) {
        this(address, bits.length, ModbusUtil.bitsToBytes(bits), bits);
    }
}
