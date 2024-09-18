package com.github.h1m3k0.modbus.core;

import com.github.h1m3k0.modbus.core.enums.ExceptionCode;
import com.github.h1m3k0.modbus.core.errorres.*;
import com.github.h1m3k0.modbus.core.request.*;
import com.github.h1m3k0.modbus.core.response.*;
import com.github.h1m3k0.modbus.core.enums.FunctionCode;
import com.github.h1m3k0.modbus.core.enums.FunctionErrorCode;
import com.github.h1m3k0.modbus.core.enums.SingleCoil;
import io.netty.buffer.ByteBuf;

public class ModbusDecoder {
    public ModbusMessage<?, ?, ?, ?, ?> resDecode(ByteBuf buf) {
        int msgCount = buf.readUnsignedShort();
        buf.readUnsignedShort();  // 00 00
        buf.readUnsignedShort();  // length
        byte slaveId = buf.readByte();
        byte code = buf.readByte();
        FunctionCode functionCode = FunctionCode.get(code);
        FunctionErrorCode functionErrorCode = FunctionErrorCode.get(code);
        ModbusMessage<?, ?, ?, ?, ?> response = null;
        try {
            switch (functionCode) {
                case ReadCoils: {
                    byte[] bytes = new byte[buf.readUnsignedByte()];
                    buf.readBytes(bytes);
                    // 正确解析后需要response.setQuantity(request.getQuantity());
                    // 这样 response的bool数组的长度就可以是quantity而不是8*n, 更精准
                    // 如 查询10个, 如果不调用setQuantity(int), 获取到的只能是16位bit, 如果调用了, 这里可以获取10位bit(舍去后面6位无意义的)
                    return response = new ReadCoilsResponse(bytes);
                }
                case ReadDiscreteInputs: {
                    byte[] bytes = new byte[buf.readUnsignedByte()];
                    buf.readBytes(bytes);
                    return response = new ReadDiscreteInputsResponse(bytes);
                }
                case ReadHoldingRegisters: {
                    byte[] value = new byte[buf.readUnsignedByte()];
                    buf.readBytes(value);
                    return response = new ReadHoldingRegistersResponse(value);
                }
                case ReadInputRegisters: {
                    byte[] value = new byte[buf.readUnsignedByte()];
                    buf.readBytes(value);
                    return response = new ReadInputRegistersResponse(value);
                }
                case WriteSingleCoil: {
                    int address = buf.readUnsignedShort();
                    int value = buf.readUnsignedShort();
                    switch (SingleCoil.get(value)) {
                        case ON:
                        case OFF:
                            return response = new WriteSingleCoilResponse(address, SingleCoil.get(value).boolValue());
                        case Undefined:
                            return null;
                    }
                }
                case WriteSingleRegister:
                    return response = new WriteSingleRegisterResponse(buf.readUnsignedShort(), buf.readShort());
                case WriteMultipleCoils:
                    return response = new WriteMultipleCoilsResponse(buf.readUnsignedShort(), buf.readUnsignedShort());
                case WriteMultipleRegisters:
                    return response = new WriteMultipleRegistersResponse(buf.readUnsignedShort(), buf.readUnsignedShort());
                case MaskWriteRegister:
                    return response = new MaskWriteRegisterResponse(buf.readUnsignedShort(), buf.readShort(), buf.readShort());
                case ReadWriteMultipleRegisters: {
                    byte[] writeValue = new byte[buf.readUnsignedByte()];
                    buf.readBytes(writeValue);
                    return response = new ReadWriteMultipleRegistersResponse(writeValue);
                }
            }
            switch (functionErrorCode) {
                case ReadCoils:
                    return response = new ReadCoilsError(ExceptionCode.get(buf.readByte()));
                case ReadDiscreteInputs:
                    return response = new ReadDiscreteInputsError(ExceptionCode.get(buf.readByte()));
                case ReadHoldingRegisters:
                    return response = new ReadHoldingRegistersError(ExceptionCode.get(buf.readByte()));
                case ReadInputRegisters:
                    return response = new ReadInputRegistersError(ExceptionCode.get(buf.readByte()));
                case WriteSingleCoil:
                    return response = new WriteSingleCoilError(ExceptionCode.get(buf.readByte()));
                case WriteSingleRegister:
                    return response = new WriteSingleRegisterError(ExceptionCode.get(buf.readByte()));
                case WriteMultipleCoils:
                    return response = new WriteMultipleCoilsError(ExceptionCode.get(buf.readByte()));
                case WriteMultipleRegisters:
                    return response = new WriteMultipleRegistersError(ExceptionCode.get(buf.readByte()));
                case MaskWriteRegister:
                    return response = new MaskWriteRegisterError(ExceptionCode.get(buf.readByte()));
                case ReadWriteMultipleRegisters:
                    return response = new ReadWriteMultipleRegistersError(ExceptionCode.get(buf.readByte()));
                default:
                    return null;
            }
        } finally {
            if (response != null) {
                response.msgCount(msgCount).slaveId(slaveId);
            }
        }
    }

    public ModbusMessage<?, ?, ?, ?, ?> reqDecode(ByteBuf buf) {
        int msgCount = buf.readUnsignedShort();
        buf.readUnsignedShort();  // 00
        buf.readUnsignedShort();  // length
        byte slaveId = buf.readByte();
        byte code = buf.readByte();
        FunctionCode functionCode = FunctionCode.get(code);
        ModbusMessage<?, ?, ?, ?, ?> request = null;
        try {
            switch (functionCode) {
                case ReadCoils:
                    return request = new ReadCoilsRequest(buf.readUnsignedShort(), buf.readUnsignedShort());
                case ReadDiscreteInputs:
                    return request = new ReadDiscreteInputsRequest(buf.readUnsignedShort(), buf.readUnsignedShort());
                case ReadHoldingRegisters:
                    return request = new ReadHoldingRegistersRequest(buf.readUnsignedShort(), buf.readUnsignedShort());
                case ReadInputRegisters:
                    return request = new ReadInputRegistersRequest(buf.readUnsignedShort(), buf.readUnsignedShort());
                case WriteSingleCoil:
                    return request = new WriteSingleCoilRequest(buf.readUnsignedShort(), SingleCoil.get(buf.readUnsignedShort()).boolValue());
                case WriteSingleRegister:
                    return request = new WriteSingleRegisterRequest(buf.readUnsignedShort(), buf.readShort());
                case WriteMultipleCoils: {
                    int address = buf.readUnsignedShort();
                    int quantity = buf.readUnsignedShort();
                    byte[] value = new byte[buf.readUnsignedByte()];
                    buf.readBytes(value);
                    return request = new WriteMultipleCoilsRequest(address, quantity, value);
                }
                case WriteMultipleRegisters: {
                    int address = buf.readUnsignedShort();
                    buf.readUnsignedShort();  // quantity
                    byte[] value = new byte[buf.readUnsignedByte()];
                    buf.readBytes(value);
                    return request = new WriteMultipleRegistersRequest(address, value);
                }
                case MaskWriteRegister:
                    return request = new MaskWriteRegisterRequest(buf.readUnsignedShort(), buf.readShort(), buf.readShort());
                case ReadWriteMultipleRegisters: {
                    int readAddress = buf.readUnsignedShort();
                    int readQuantity = buf.readUnsignedShort();
                    int writeAddress = buf.readUnsignedShort();
                    buf.readUnsignedShort();  // writeQuantity
                    byte[] writeValue = new byte[buf.readUnsignedByte()];
                    buf.readBytes(writeValue);
                    return request = new ReadWriteMultipleRegistersRequest(readAddress, readQuantity, writeAddress, writeValue);
                }
                default:
                    return null;
            }
        } finally {
            if (request != null) {
                request.msgCount(msgCount).slaveId(slaveId);
            }
        }
    }
}
