package com.github.h1m3k0.modbus.core.response;

import com.github.h1m3k0.modbus.core.errorres.ReadCoilsError;
import com.github.h1m3k0.modbus.core.function.ReadCoilsFunction;
import com.github.h1m3k0.modbus.core.request.ReadCoilsRequest;
import com.github.h1m3k0.modbus.core.utils.ModbusUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class ReadCoilsResponse extends ModbusResponse<ReadCoilsFunction, ReadCoilsRequest, ReadCoilsResponse, ReadCoilsError> {
    private final int quantity;  // 临时
    private final byte[] bytes;  // 封装数据
    private final boolean[] bits;  // 解析数据

    private ReadCoilsResponse(int quantity, byte[] bytes, boolean[] bits) {
        super.function = ReadCoilsFunction.Instance();
        this.quantity = quantity;
        this.bytes = bytes;
        this.bits = bits;
    }

    public ReadCoilsResponse(int quantity, byte[] bytes) {
        this(quantity, bytes, ModbusUtil.bytesToBits(bytes, quantity));
    }

    public ReadCoilsResponse(byte[] bytes) {
        this(bytes.length << 3, bytes);
    }

    public ReadCoilsResponse(boolean[] bits) {
        this(bits.length, ModbusUtil.bitsToBytes(bits), bits);
    }
}
