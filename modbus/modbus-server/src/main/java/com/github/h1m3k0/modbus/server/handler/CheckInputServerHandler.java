package com.github.h1m3k0.modbus.server.handler;

import com.github.h1m3k0.modbus.core.ModbusMessage;
import com.github.h1m3k0.modbus.core.enums.ExceptionCode;
import com.github.h1m3k0.modbus.core.enums.SingleCoil;
import com.github.h1m3k0.modbus.core.errorres.*;
import com.github.h1m3k0.modbus.core.request.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class CheckInputServerHandler extends SimpleChannelInboundHandler<ModbusMessage<?, ?, ?, ?, ?>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ModbusMessage<?, ?, ?, ?, ?> msg) throws Exception {
        ModbusError<?, ?, ?, ?> modbusError = null;
        switch (msg.function().code()) {
            case ReadCoils: {
                ReadCoilsRequest request = (ReadCoilsRequest) msg;
                if (0x0001 <= request.quantity() && request.quantity() <= 0x07D0) {
                    ctx.fireChannelRead(request);
                    return;
                }
                modbusError = new ReadCoilsError(ExceptionCode.Input);
            }
            break;
            case ReadDiscreteInputs: {
                ReadDiscreteInputsRequest request = (ReadDiscreteInputsRequest) msg;
                if (0x0001 <= request.quantity() && request.quantity() <= 0x07D0) {
                    ctx.fireChannelRead(request);
                    return;
                }
                modbusError = new ReadDiscreteInputsError(ExceptionCode.Input);
            }
            break;
            case ReadHoldingRegisters: {
                ReadHoldingRegistersRequest request = (ReadHoldingRegistersRequest) msg;
                if (0x0001 <= request.quantity() && request.quantity() <= 0x007D) {
                    ctx.fireChannelRead(request);
                    return;
                }
                modbusError = new ReadHoldingRegistersError(ExceptionCode.Input);
            }
            break;
            case ReadInputRegisters: {
                ReadInputRegistersRequest request = (ReadInputRegistersRequest) msg;
                if (0x0001 <= request.quantity() && request.quantity() <= 0x007D) {
                    ctx.fireChannelRead(request);
                    return;
                }
                modbusError = new ReadInputRegistersError(ExceptionCode.Input);
            }
            break;
            case WriteSingleCoil: {
                WriteSingleCoilRequest request = (WriteSingleCoilRequest) msg;
                if (request.value() == SingleCoil.ON || request.value() == SingleCoil.OFF) {
                    ctx.fireChannelRead(request);
                    return;
                }
                modbusError = new WriteSingleCoilError(ExceptionCode.Input);
            }
            break;
            case WriteSingleRegister: {  // 0x0000 <= value <= 0xFFFF  // always true
                ctx.fireChannelRead(msg);
                return;
            }
//                break;
            case WriteMultipleCoils: {
                WriteMultipleCoilsRequest request = (WriteMultipleCoilsRequest) msg;
                if (0x0001 <= request.quantity() && request.quantity() <= 0x7B0 && request.bytes().length == request.quantity()) {
                    ctx.fireChannelRead(request);
                    return;
                }
                modbusError = new WriteMultipleCoilsError(ExceptionCode.Input);
            }
            break;
            case WriteMultipleRegisters: {
                WriteMultipleRegistersRequest request = (WriteMultipleRegistersRequest) msg;
                if (0x0001 <= request.value().length && request.quantity() <= 0x07B && request.value().length == request.quantity() << 1) {
                    ctx.fireChannelRead(request);
                    return;
                }
                modbusError = new WriteMultipleRegistersError(ExceptionCode.Input);
            }
            break;
            case MaskWriteRegister: {
                MaskWriteRegisterRequest request = (MaskWriteRegisterRequest) msg;
                ctx.fireChannelRead(request);
                return;
            }
//                break;
            case ReadWriteMultipleRegisters: {
                ReadWriteMultipleRegistersRequest request = (ReadWriteMultipleRegistersRequest) msg;
                if (0x0001 <= request.readQuantity() && request.readQuantity() <= 0x007D
                        && 0x0001 <= request.writeQuantity() && request.writeQuantity() <= 0x0079) {
                    ctx.fireChannelRead(request);
                    return;
                }
                modbusError = new ReadWriteMultipleRegistersError(ExceptionCode.Input);
            }
            break;
            default: {
                modbusError = new UndefinedError(ExceptionCode.FunctionCode, msg.code());
            }
            break;
        }
        // Input Check Error
        ctx.channel().writeAndFlush(modbusError
                .msgCount(msg.msgCount())
                .slaveId(msg.slaveId()));
    }

}
