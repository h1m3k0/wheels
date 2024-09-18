package com.github.h1m3k0.modbus.server;

import com.github.h1m3k0.common.netty.decoder.HeadLengthDecoder;
import com.github.h1m3k0.common.netty.decoder.HeadLengthDecoderFactory;
import com.github.h1m3k0.common.netty.transfer.IntTransfer;
import com.github.h1m3k0.modbus.core.ModbusConsumer;
import com.github.h1m3k0.modbus.core.errorres.*;
import com.github.h1m3k0.modbus.core.function.*;
import com.github.h1m3k0.modbus.core.request.*;
import com.github.h1m3k0.modbus.core.response.*;
import com.github.h1m3k0.modbus.server.handler.*;
import com.github.h1m3k0.modbus.server.functional.ModbusFunctional;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ModbusServer {

    private final int port;
    private final HeadLengthDecoder headLengthDecoder;
    private final ModbusServerCodec modbusServerCodec;
    private final CheckInputServerHandler checkInputServerHandler;
    private final ReadCoilsServerHandler readCoilsServerHandler;
    private final ReadDiscreteInputsServerHandler readDiscreteInputsServerHandler;
    private final ReadHoldingRegistersServerHandler readHoldingRegistersServerHandler;
    private final ReadInputRegistersServerHandler readInputRegistersServerHandler;
    private final WriteSingleCoilServerHandler writeSingleCoilServerHandler;
    private final WriteSingleRegisterServerHandler writeSingleRegisterServerHandler;
    private final WriteMultipleCoilsServerHandler writeMultipleCoilsServerHandler;
    private final WriteMultipleRegistersServerHandler writeMultipleRegistersServerHandler;
    private final MaskWriteRegisterServerHandler maskWriteRegisterServerHandler;
    private final ReadWriteMultipleRegistersServerHandler readWriteMultipleRegistersServerHandler;

    ModbusServer(
            int port,
            ModbusFunctional<ReadCoilsFunction, ReadCoilsRequest, ReadCoilsResponse, ReadCoilsError> readCoilsFunction,
            ModbusFunctional<ReadDiscreteInputsFunction, ReadDiscreteInputsRequest, ReadDiscreteInputsResponse, ReadDiscreteInputsError> readDiscreteInputsFunction,
            ModbusFunctional<ReadHoldingRegistersFunction, ReadHoldingRegistersRequest, ReadHoldingRegistersResponse, ReadHoldingRegistersError> readHoldingRegistersFunction,
            ModbusFunctional<ReadInputRegistersFunction, ReadInputRegistersRequest, ReadInputRegistersResponse, ReadInputRegistersError> readInputRegistersFunction,
            ModbusConsumer<WriteSingleCoilRequest> writeSingleCoilConsumer,
            ModbusConsumer<WriteSingleRegisterRequest> writeSingleRegisterConsumer,
            ModbusConsumer<WriteMultipleCoilsRequest> writeMultipleCoilsConsumer,
            ModbusConsumer<WriteMultipleRegistersRequest> writeMultipleRegistersConsumer,
            ModbusConsumer<MaskWriteRegisterRequest> maskWriteRegisterRequestConsumer,
            ModbusFunctional<ReadWriteMultipleRegistersFunction, ReadWriteMultipleRegistersRequest, ReadWriteMultipleRegistersResponse, ReadWriteMultipleRegistersError> readWriteMultipleFunction
    ) {
        this.port = port;
        headLengthDecoder = HeadLengthDecoderFactory.builder(new Byte[]{null, null, 0, 0}, IntTransfer.buildDefault16()).build();
        modbusServerCodec = new ModbusServerCodec();
        checkInputServerHandler = new CheckInputServerHandler();
        readCoilsServerHandler = new ReadCoilsServerHandler(readCoilsFunction);
        readDiscreteInputsServerHandler = new ReadDiscreteInputsServerHandler(readDiscreteInputsFunction);
        readHoldingRegistersServerHandler = new ReadHoldingRegistersServerHandler(readHoldingRegistersFunction);
        readInputRegistersServerHandler = new ReadInputRegistersServerHandler(readInputRegistersFunction);
        writeSingleCoilServerHandler = new WriteSingleCoilServerHandler(writeSingleCoilConsumer);
        writeSingleRegisterServerHandler = new WriteSingleRegisterServerHandler(writeSingleRegisterConsumer);
        writeMultipleCoilsServerHandler = new WriteMultipleCoilsServerHandler(writeMultipleCoilsConsumer);
        writeMultipleRegistersServerHandler = new WriteMultipleRegistersServerHandler(writeMultipleRegistersConsumer);
        maskWriteRegisterServerHandler = new MaskWriteRegisterServerHandler(maskWriteRegisterRequestConsumer);
        readWriteMultipleRegistersServerHandler = new ReadWriteMultipleRegistersServerHandler(readWriteMultipleFunction);
    }

    public void init() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.channel(NioServerSocketChannel.class)
                    .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                    // 返回丢包
                    .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(1, 1024 * 1024 * 8))
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(headLengthDecoder);
                            ch.pipeline().addLast(modbusServerCodec);
                            ch.pipeline().addLast(checkInputServerHandler);
                            ch.pipeline().addLast(readCoilsServerHandler);
                            ch.pipeline().addLast(readDiscreteInputsServerHandler);
                            ch.pipeline().addLast(readHoldingRegistersServerHandler);
                            ch.pipeline().addLast(readInputRegistersServerHandler);
                            ch.pipeline().addLast(writeSingleCoilServerHandler);
                            ch.pipeline().addLast(writeSingleRegisterServerHandler);
                            ch.pipeline().addLast(writeMultipleCoilsServerHandler);
                            ch.pipeline().addLast(writeMultipleRegistersServerHandler);
                            ch.pipeline().addLast(maskWriteRegisterServerHandler);
                            ch.pipeline().addLast(readWriteMultipleRegistersServerHandler);
                        }
                    });
            ChannelFuture f = b.bind(this.port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
