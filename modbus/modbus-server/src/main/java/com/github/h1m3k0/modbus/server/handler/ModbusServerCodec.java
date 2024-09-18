package com.github.h1m3k0.modbus.server.handler;

import com.github.h1m3k0.modbus.core.ModbusDecoder;
import com.github.h1m3k0.modbus.core.ModbusEncoder;
import com.github.h1m3k0.modbus.core.ModbusMessage;
import com.github.h1m3k0.modbus.core.errorres.ModbusError;
import com.github.h1m3k0.modbus.core.response.ModbusResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

@ChannelHandler.Sharable
public class ModbusServerCodec extends MessageToMessageCodec<ByteBuf, ModbusMessage<?, ?, ?, ?, ?>> {
    private final ModbusDecoder modbusDecoder = new ModbusDecoder();
    private final ModbusEncoder modbusEncoder = new ModbusEncoder();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ModbusMessage<?, ?, ?, ?, ?> message, List<Object> list) throws Exception {
        if (message instanceof ModbusResponse) {
            list.add(modbusEncoder.encode((ModbusResponse<?, ?, ?, ?>) message));
        } else if (message instanceof ModbusError) {
            list.add(modbusEncoder.encode((ModbusError<?, ?, ?, ?>) message));
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {
        ModbusMessage<?, ?, ?, ?, ?> modbusMessage = modbusDecoder.reqDecode(buf);
        if (modbusMessage != null) {
            list.add(modbusMessage);
        }
    }
}
