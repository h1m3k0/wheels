package com.github.h1m3k0.modbus.core;

import com.github.h1m3k0.modbus.core.request.*;
import com.github.h1m3k0.modbus.core.response.*;
import com.github.h1m3k0.modbus.core.errorres.ModbusError;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ModbusEncoder {
    public ByteBuf encode(ModbusRequest<?, ?, ?, ?> message) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeShort(message.msgCount());
        buf.writeBytes(new byte[]{0, 0, 0, 0});
        buf.writeByte(message.slaveId());
        buf.writeByte(message.code());
        switch (message.function().code()) {
            case ReadCoils: {
                ReadCoilsRequest request = (ReadCoilsRequest) message;
                buf.writeShort(request.address());
                buf.writeShort(request.quantity());
            }
            break;
            case ReadDiscreteInputs: {
                ReadDiscreteInputsRequest request = (ReadDiscreteInputsRequest) message;
                buf.writeShort(request.address());
                buf.writeShort(request.quantity());
            }
            break;
            case ReadHoldingRegisters: {
                ReadHoldingRegistersRequest request = (ReadHoldingRegistersRequest) message;
                buf.writeShort(request.address());
                buf.writeShort(request.quantity());
            }
            break;
            case ReadInputRegisters: {
                ReadInputRegistersRequest request = (ReadInputRegistersRequest) message;
                buf.writeShort(request.address());
                buf.writeShort(request.quantity());
            }
            break;
            case WriteSingleCoil: {
                WriteSingleCoilRequest request = (WriteSingleCoilRequest) message;
                buf.writeShort(request.address());
                buf.writeShort(request.value().intValue());
            }
            break;
            case WriteSingleRegister: {
                WriteSingleRegisterRequest request = (WriteSingleRegisterRequest) message;
                buf.writeShort(request.address());
                buf.writeShort(request.value());
            }
            break;
            case WriteMultipleCoils: {
                WriteMultipleCoilsRequest request = (WriteMultipleCoilsRequest) message;
                buf.writeShort(request.address());
                buf.writeShort(request.quantity());
                buf.writeByte(request.bytes().length);
                buf.writeBytes(request.bytes());
            }
            break;
            case WriteMultipleRegisters: {
                WriteMultipleRegistersRequest request = (WriteMultipleRegistersRequest) message;
                buf.writeShort(request.address());
                buf.writeShort(request.value().length / 2);
                buf.writeByte(request.value().length);
                buf.writeBytes(request.value());
            }
            break;
            case MaskWriteRegister: {
                MaskWriteRegisterRequest request = (MaskWriteRegisterRequest) message;
                buf.writeShort(request.address());
                buf.writeShort(request.andMask());
                buf.writeShort(request.orMask());
            }
            break;
            case ReadWriteMultipleRegisters: {
                ReadWriteMultipleRegistersRequest request = (ReadWriteMultipleRegistersRequest) message;
                buf.writeShort(request.readAddress());
                buf.writeShort(request.readQuantity());
                buf.writeShort(request.writeAddress());
                buf.writeShort(request.writeValue().length / 2);
                buf.writeByte(request.writeValue().length);
                buf.writeBytes(request.writeValue());
            }
            break;
        }
        buf.setShort(4, buf.readableBytes() - 6);
        return buf;
    }

    public ByteBuf encode(ModbusResponse<?, ?, ?, ?> message) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeShort(message.msgCount());
        buf.writeBytes(new byte[]{0, 0, 0, 0});
        buf.writeByte(message.slaveId());
        buf.writeByte(message.code());
        switch (message.function().code()) {
            case ReadCoils: {
                ReadCoilsResponse response = (ReadCoilsResponse) message;
                buf.writeByte(response.bytes().length);
                buf.writeBytes(response.bytes());
            }
            break;
            case ReadDiscreteInputs: {
                ReadDiscreteInputsResponse response = (ReadDiscreteInputsResponse) message;
                buf.writeByte(response.bytes().length);
                buf.writeBytes(response.bytes());
            }
            break;
            case ReadHoldingRegisters: {
                ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) message;
                buf.writeByte(response.value().length);
                buf.writeBytes(response.value());
            }
            break;
            case ReadInputRegisters: {
                ReadInputRegistersResponse response = (ReadInputRegistersResponse) message;
                buf.writeByte(response.value().length);
                buf.writeBytes(response.value());
            }
            break;
            case WriteSingleCoil: {
                WriteSingleCoilResponse response = (WriteSingleCoilResponse) message;
                buf.writeShort(response.address());
                buf.writeShort(response.value().intValue());
            }
            break;
            case WriteSingleRegister: {
                WriteSingleRegisterResponse response = (WriteSingleRegisterResponse) message;
                buf.writeShort(response.address());
                buf.writeShort(response.data());
            }
            break;
            case WriteMultipleCoils: {
                WriteMultipleCoilsResponse response = (WriteMultipleCoilsResponse) message;
                buf.writeShort(response.address());
                buf.writeShort(response.quantity());
            }
            break;
            case WriteMultipleRegisters: {
                WriteMultipleRegistersResponse response = (WriteMultipleRegistersResponse) message;
                buf.writeShort(response.address());
                buf.writeShort(response.quantity());
            }
            break;
            case MaskWriteRegister: {
                MaskWriteRegisterResponse response = (MaskWriteRegisterResponse) message;
                buf.writeShort(response.address());
                buf.writeShort(response.andMask());
                buf.writeShort(response.orMask());
            }
            break;
            case ReadWriteMultipleRegisters: {
                ReadWriteMultipleRegistersResponse response = (ReadWriteMultipleRegistersResponse) message;
                buf.writeByte(response.readValue().length);
                buf.writeBytes(response.readValue());
            }
            break;
        }
        buf.setShort(4, buf.readableBytes() - 6);
        return buf;
    }

    public ByteBuf encode(ModbusError<?, ?, ?, ?> message) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeShort(message.msgCount());
        buf.writeBytes(new byte[]{0, 0, 0, 0});
        buf.writeByte(message.slaveId());
        buf.writeByte(message.code());

        buf.writeByte(message.exceptionCode().code());

        buf.setShort(4, buf.readableBytes() - 6);
        return buf;
    }
}
