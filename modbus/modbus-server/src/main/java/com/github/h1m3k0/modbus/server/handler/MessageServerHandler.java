package com.github.h1m3k0.modbus.server.handler;

import com.github.h1m3k0.modbus.core.ModbusException;
import com.github.h1m3k0.modbus.core.enums.ExceptionCode;
import com.github.h1m3k0.modbus.core.errorres.ModbusError;
import com.github.h1m3k0.modbus.core.errorres.UndefinedError;
import com.github.h1m3k0.modbus.core.function.ModbusFunction;
import com.github.h1m3k0.modbus.core.request.ModbusRequest;
import com.github.h1m3k0.modbus.core.response.ModbusResponse;
import com.github.h1m3k0.modbus.server.functional.ModbusFunctional;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@ChannelHandler.Sharable
public abstract class MessageServerHandler<
        function extends ModbusFunction<function, request, response, error>,
        request extends ModbusRequest<function, request, response, error>,
        response extends ModbusResponse<function, request, response, error>,
        error extends ModbusError<function, request, response, error>> extends SimpleChannelInboundHandler<request> {
    protected ModbusFunctional<function, request, response, error> function;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, request request) throws Exception {
        if (function != null) {
            response response;
            try {
                response = function.apply(request);
                if (response != null) {
                    response.function(request.function());
                    response.msgCount(request.msgCount());
                    response.slaveId(request.slaveId());
                    ctx.channel().writeAndFlush(response);
                }
            } catch (ModbusException e) {
                ModbusError<?, ?, ?, ?> modbusError = e.message();
                if (modbusError == null) {
                    modbusError = new UndefinedError(ExceptionCode.Process, request.code());
                }
                ctx.channel().writeAndFlush(modbusError
                        .msgCount(request.msgCount())
                        .slaveId(request.slaveId()));
            }
        } else {
            UndefinedError modbusError = new UndefinedError(ExceptionCode.FunctionCode, request.code());
            ctx.writeAndFlush(modbusError
                    .msgCount(request.msgCount())
                    .slaveId(request.slaveId()));
        }
    }
}
