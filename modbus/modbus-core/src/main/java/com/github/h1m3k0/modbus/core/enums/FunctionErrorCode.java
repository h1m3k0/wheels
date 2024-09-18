package com.github.h1m3k0.modbus.core.enums;

import com.github.h1m3k0.modbus.core.function.*;
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
public enum FunctionErrorCode {
    ReadCoils((byte) 0x81, DataModel.Coils, "读线圈状态错误", ReadCoilsFunction::Instance),
    ReadDiscreteInputs((byte) 0x82, DataModel.DiscreteInput, "读离散输入状态错误", ReadDiscreteInputsFunction::Instance),
    ReadHoldingRegisters((byte) 0x83, DataModel.HoldingRegisters, "读保持寄存器错误", ReadHoldingRegistersFunction::Instance),
    ReadInputRegisters((byte) 0x84, DataModel.InputRegisters, "读输入寄存器错误", ReadInputRegistersFunction::Instance),
    WriteSingleCoil((byte) 0x85, DataModel.Coils, "写单个线圈错误", WriteSingleCoilFunction::Instance),
    WriteSingleRegister((byte) 0x86, DataModel.HoldingRegisters, "写单个保持寄存器错误", WriteSingleRegisterFunction::Instance),
    WriteMultipleCoils((byte) 0x8F, DataModel.Coils, "写多个线圈错误", WriteMultipleCoilsFunction::Instance),
    WriteMultipleRegisters((byte) 0x90, DataModel.HoldingRegisters, "写多个保持寄存器错误", WriteMultipleRegistersFunction::Instance),
    MaskWriteRegister((byte) 0x96, DataModel.HoldingRegisters, "掩码写入保持寄存器错误", MaskWriteRegisterFunction::Instance),
    ReadWriteMultipleRegisters((byte) 0x97, DataModel.HoldingRegisters, "读写多个保持寄存器错误", ReadWriteMultipleRegistersFunction::Instance),

    Undefined(null, null, "未定义指令", UndefinedFunction::Instance),
    ;
    private final Byte value;
    private final DataModel dataModel;
    private final String description;
    private final Supplier<ModbusFunction<?, ?, ?, ?>> function;
    private static final Map<Byte, FunctionErrorCode> map = Arrays.stream(FunctionErrorCode.values()).collect(Collectors.toMap(FunctionErrorCode::value, errorCode -> errorCode));

    public static FunctionErrorCode get(byte code) {
        if (map.containsKey(code)) {
            return map.get(code);
        }
        return Undefined;
    }
}
