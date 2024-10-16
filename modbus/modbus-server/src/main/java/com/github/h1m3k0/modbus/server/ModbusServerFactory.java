package com.github.h1m3k0.modbus.server;


import com.github.h1m3k0.modbus.server.functional.ModbusConsumer;
import com.github.h1m3k0.modbus.core.errorres.*;
import com.github.h1m3k0.modbus.core.function.*;
import com.github.h1m3k0.modbus.core.request.*;
import com.github.h1m3k0.modbus.core.response.*;
import com.github.h1m3k0.modbus.core.utils.ModbusUtil;
import com.github.h1m3k0.modbus.server.functional.*;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ModbusServerFactory {
    public static Builder builder(int port) {
        return new Builder(port);
    }

    @Accessors(chain = true)
    @Setter
    public static class Builder {
        private final int port;
        private ModbusFunctional<ReadCoilsFunction, ReadCoilsRequest, ReadCoilsResponse, ReadCoilsError> readCoils;
        private ReadCoilsFunctional readCoilsFunctional;
        private WriteMultipleCoilsFunctional writeMultipleCoilsFunctional;
        private ModbusFunctional<ReadDiscreteInputsFunction, ReadDiscreteInputsRequest, ReadDiscreteInputsResponse, ReadDiscreteInputsError> readDiscreteInputs;
        private ReadDiscreteInputsFunctional readDiscreteInputsFunctional;
        private ModbusFunctional<ReadHoldingRegistersFunction, ReadHoldingRegistersRequest, ReadHoldingRegistersResponse, ReadHoldingRegistersError> readHoldingRegisters;
        private ReadHoldingRegistersFunctional readHoldingRegistersFunctional;
        private WriteMultipleRegistersFunctional writeMultipleRegistersFunctional;
        private ModbusFunctional<ReadInputRegistersFunction, ReadInputRegistersRequest, ReadInputRegistersResponse, ReadInputRegistersError> readInputRegisters;
        private ReadInputRegistersFunctional readInputRegistersFunctional;
        private ModbusConsumer<WriteSingleCoilRequest> writeSingleCoil;
        private ModbusConsumer<WriteSingleRegisterRequest> writeSingleRegister;
        private ModbusConsumer<WriteMultipleCoilsRequest> writeMultipleCoils;
        private ModbusConsumer<WriteMultipleRegistersRequest> writeMultipleRegisters;
        private ModbusConsumer<MaskWriteRegisterRequest> maskWriteRegister;
        private ModbusFunctional<ReadWriteMultipleRegistersFunction, ReadWriteMultipleRegistersRequest, ReadWriteMultipleRegistersResponse, ReadWriteMultipleRegistersError> readWriteMultipleRegisters;

        private Builder(int port) {
            this.port = port;
        }

        public ModbusServer build() {
            return new ModbusServer(port,
                    readCoils != null || readCoilsFunctional == null ? readCoils :
                            req -> new ReadCoilsResponse(readCoilsFunctional.read(req.address(), req.quantity())),
                    readDiscreteInputs != null || readDiscreteInputsFunctional == null ? readDiscreteInputs :
                            req -> new ReadDiscreteInputsResponse(readDiscreteInputsFunctional.read(req.address(), req.quantity())),
                    readHoldingRegisters != null || readHoldingRegistersFunctional == null ? readHoldingRegisters :
                            req -> new ReadHoldingRegistersResponse(readHoldingRegistersFunctional.read(req.address(), req.quantity())),
                    readInputRegisters != null || readInputRegistersFunctional == null ? readInputRegisters :
                            req -> new ReadInputRegistersResponse(readInputRegistersFunctional.read(req.address(), req.quantity())),
                    writeSingleCoil != null || writeMultipleCoilsFunctional == null ? writeSingleCoil :
                            req -> writeMultipleCoilsFunctional.write(req.address(), new boolean[]{req.value().boolValue()}),
                    writeSingleRegister != null || writeMultipleRegistersFunctional == null ? writeSingleRegister :
                            req -> writeMultipleRegistersFunctional.write(req.address(), ModbusUtil.shortToBytes(req.value())),
                    writeMultipleCoils != null || writeMultipleCoilsFunctional == null ? writeMultipleCoils :
                            req -> writeMultipleCoilsFunctional.write(req.address(), req.bits()),
                    writeMultipleRegisters != null || writeMultipleRegistersFunctional == null ? writeMultipleRegisters :
                            req -> writeMultipleRegistersFunctional.write(req.address(), req.value()),

                    maskWriteRegister != null || writeMultipleRegistersFunctional == null || readInputRegistersFunctional == null ? maskWriteRegister :
                            req -> {
                                byte[] read = readHoldingRegistersFunctional.read(req.address(), 1);
                                int current = (read[0] << 8) | read[1];
                                int andMask = Short.toUnsignedInt(req.andMask());
                                int orMask = Short.toUnsignedInt(req.orMask());
                                int result = (current & andMask) | (orMask & (~andMask));
                                writeMultipleRegistersFunctional.write(req.address(), ModbusUtil.shortToBytes((short) result));
                            },
                    readWriteMultipleRegisters != null || readHoldingRegistersFunctional == null || writeMultipleRegistersFunctional == null ? readWriteMultipleRegisters :
                            req -> {
                                writeMultipleRegistersFunctional.write(req.writeAddress(), req.writeValue());
                                return new ReadWriteMultipleRegistersResponse(readHoldingRegistersFunctional.read(req.readAddress(), req.readQuantity()));
                            }
            );
        }
    }
}
