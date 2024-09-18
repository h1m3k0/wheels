package com.github.h1m3k0.modbus.core.enums;

import com.github.h1m3k0.modbus.core.function.*;
import com.github.h1m3k0.protocols.modbus.core.function.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Accessors(chain = true, fluent = true)
public enum FunctionCode {
    ReadCoils((byte) 0x01, DataModel.Coils, "读线圈状态", ReadCoilsFunction::Instance),
    ReadDiscreteInputs((byte) 0x02, DataModel.DiscreteInput, "读离散输入状态", ReadDiscreteInputsFunction::Instance),
    ReadHoldingRegisters((byte) 0x03, DataModel.HoldingRegisters, "读保持寄存器", ReadHoldingRegistersFunction::Instance),
    ReadInputRegisters((byte) 0x04, DataModel.InputRegisters, "读输入寄存器", ReadInputRegistersFunction::Instance),
    WriteSingleCoil((byte) 0x05, DataModel.Coils, "写单个线圈", WriteSingleCoilFunction::Instance),
    WriteSingleRegister((byte) 0x06, DataModel.HoldingRegisters, "写单个保持寄存器", WriteSingleRegisterFunction::Instance),
    WriteMultipleCoils((byte) 0x0F, DataModel.Coils, "写多个线圈", WriteMultipleCoilsFunction::Instance),
    WriteMultipleRegisters((byte) 0x10, DataModel.HoldingRegisters, "写多个保持寄存器", WriteMultipleRegistersFunction::Instance),
    MaskWriteRegister((byte) 0x16, DataModel.HoldingRegisters, "掩码写入保持寄存器", MaskWriteRegisterFunction::Instance),
    ReadWriteMultipleRegisters((byte) 0x17, DataModel.HoldingRegisters, "读写多个保持寄存器", ReadWriteMultipleRegistersFunction::Instance),

    Undefined(null, null, "未定义指令", UndefinedFunction::Instance),
    ;
    private final Byte value;
    private final DataModel dataModel;
    private final String description;
    private final Supplier<ModbusFunction<?, ?, ?, ?>> function;
    private static final Map<Byte, FunctionCode> map = Arrays.stream(FunctionCode.values()).collect(Collectors.toMap(FunctionCode::value, functionCode -> functionCode));

    public static FunctionCode get(byte code) {
        if (map.containsKey(code)) {
            return map.get(code);
        }
        return Undefined;
    }
}