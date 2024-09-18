package com.github.h1m3k0.modbus.client.handler;

import com.github.h1m3k0.modbus.core.ModbusDecoder;
import com.github.h1m3k0.modbus.core.ModbusEncoder;
import com.github.h1m3k0.modbus.core.ModbusMessage;
import com.github.h1m3k0.modbus.core.request.ModbusRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

@ChannelHandler.Sharable
public class MessageClientCodec extends MessageToMessageCodec<ByteBuf, ModbusMessage<?, ?, ?, ?, ?>> {
    private final ModbusDecoder modbusDecoder = new ModbusDecoder();
    private final ModbusEncoder modbusEncoder = new ModbusEncoder();

    @Override
    protected void encode(ChannelHandlerContext ctx, ModbusMessage<?, ?, ?, ?, ?> message, List<Object> list) throws Exception {
        if (message instanceof ModbusRequest) {
            list.add(modbusEncoder.encode((ModbusRequest<?, ?, ?, ?>) message));
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        ModbusMessage<?, ?, ?, ?, ?> modbusMessage = modbusDecoder.resDecode(buf);
        if (modbusMessage != null) {
            list.add(modbusMessage);
        }
    }
}















