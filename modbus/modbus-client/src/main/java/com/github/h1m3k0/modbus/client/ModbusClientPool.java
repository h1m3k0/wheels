package com.github.h1m3k0.modbus.client;

import com.github.h1m3k0.common.netty.client.ClientPool;
import com.github.h1m3k0.modbus.client.handler.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.function.Supplier;

public class ModbusClientPool extends ClientPool<ModbusConfig, ModbusClient, ModbusClientPool> {
    private final MessageClientCodec messageClientCodec = new MessageClientCodec();
    private final ReadCoilsClientHandler readCoilsClientHandler = new ReadCoilsClientHandler();
    private final ReadDiscreteInputsClientHandler readDiscreteInputsClientHandler = new ReadDiscreteInputsClientHandler();
    private final ReadHoldingRegistersClientHandler readHoldingRegistersClientHandler = new ReadHoldingRegistersClientHandler();
    private final ReadInputRegistersClientHandler readInputRegistersClientHandler = new ReadInputRegistersClientHandler();
    private final WriteSingleCoilClientHandler writeSingleCoilClientHandler = new WriteSingleCoilClientHandler();
    private final WriteSingleRegisterClientHandler writeSingleRegisterClientHandler = new WriteSingleRegisterClientHandler();
    private final WriteMultipleCoilsClientHandler writeMultipleCoilsClientHandler = new WriteMultipleCoilsClientHandler();
    private final WriteMultipleRegistersClientHandler writeMultipleRegistersClientHandler = new WriteMultipleRegistersClientHandler();
    private final MaskWriteRegisterClientHandler maskWriteRegisterClientHandler = new MaskWriteRegisterClientHandler();
    private final ReadWriteMultipleRegistersClientHandler readWriteMultipleRegistersClientHandler = new ReadWriteMultipleRegistersClientHandler();
    private final MessageExceptionClientHandler messageExceptionClientHandler = new MessageExceptionClientHandler();

    /**
     * 不需要handler
     */
    public ModbusClientPool() {
        this((Supplier<ChannelHandler>) null);
    }

    /**
     * handler不是@ChannelHandler.Sharable
     */
    public ModbusClientPool(Supplier<ChannelHandler> netHandler) {
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(netHandler != null ? netHandler.get() : null);
                ch.pipeline().addLast(messageClientCodec);
                ch.pipeline().addLast(readCoilsClientHandler);
                ch.pipeline().addLast(readDiscreteInputsClientHandler);
                ch.pipeline().addLast(readHoldingRegistersClientHandler);
                ch.pipeline().addLast(readInputRegistersClientHandler);
                ch.pipeline().addLast(writeSingleCoilClientHandler);
                ch.pipeline().addLast(writeSingleRegisterClientHandler);
                ch.pipeline().addLast(writeMultipleCoilsClientHandler);
                ch.pipeline().addLast(writeMultipleRegistersClientHandler);
                ch.pipeline().addLast(maskWriteRegisterClientHandler);
                ch.pipeline().addLast(readWriteMultipleRegistersClientHandler);
                ch.pipeline().addLast(messageExceptionClientHandler);
            }
        });
    }

    /**
     * handler是@ChannelHandler.Sharable
     */
    public ModbusClientPool(ChannelHandler netHandler) {
        this(() -> netHandler);
    }

    @Override
    public ModbusClient newClient(ModbusConfig config) {
        return new ModbusClient(this, config);
    }
}
