package com.github.h1m3k0.modbus.server;

import com.github.h1m3k0.modbus.core.response.*;
import com.github.h1m3k0.modbus.server.functional.*;
import com.github.h1m3k0.protocols.modbus.server.functional.*;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ModbusDataServerFactory {
    public static Builder builder(int port) {
        return new Builder(port);
    }

    @Accessors(chain = true)
    @Setter
    public static class Builder {
        private final int port;
        private ReadCoilsFunctional readCoils;
        private ReadDiscreteInputsFunctional readDiscreteInputs;
        private ReadHoldingRegistersFunctional readHoldingRegisters;
        private ReadInputRegistersFunctional readInputRegisters;
        private WriteSingleCoilFunctional writeSingleCoil;
        private WriteSingleRegisterFunctional writeSingleRegister;
        private WriteMultipleCoilsFunctional writeMultipleCoils;
        private WriteMultipleRegistersFunctional writeMultipleRegisters;

        private Builder(int port) {
            this.port = port;
        }

        public ModbusServer build() {
            if (writeSingleCoil != null && writeMultipleCoils == null) {
                writeMultipleCoils = (address, value) -> {
                    for (int i = 0; i < value.length; i++) {
                        writeSingleCoil.write(address + i, value[i]);
                    }
                };
            }
            if (writeSingleRegister != null && writeMultipleRegisters == null) {
                writeMultipleRegisters = (address, value) -> {
                    for (int i = 0; i < value.length; i += 2) {
                        writeSingleRegister.write(address + i / 2, (short) ((value[i] << 8) | value[i + 1]));
                    }
                };
            }
            return new ModbusServer(port,
                    readCoils == null ? null : req -> new ReadCoilsResponse(readCoils.read(req.address(), req.quantity())),
                    readDiscreteInputs == null ? null : req -> new ReadDiscreteInputsResponse(readDiscreteInputs.read(req.address(), req.quantity())),
                    readHoldingRegisters == null ? null : req -> new ReadHoldingRegistersResponse(readHoldingRegisters.read(req.address(), req.quantity())),
                    readInputRegisters == null ? null : req -> new ReadInputRegistersResponse(readInputRegisters.read(req.address(), req.quantity())),
                    writeSingleCoil == null ? null : req -> writeSingleCoil.write(req.address(), req.value().boolValue()),
                    writeSingleRegister == null ? null : req -> writeSingleRegister.write(req.address(), req.value()),
                    writeMultipleCoils == null ? null : req -> writeMultipleCoils.write(req.address(), req.bits()),
                    writeMultipleRegisters == null ? null : req -> writeMultipleRegisters.write(req.address(), req.value()),
                    writeSingleRegister == null || readHoldingRegisters == null ? null : req -> {
                        byte[] read = readHoldingRegisters.read(req.address(), 1);
                        int current = (read[0] << 8) | read[1];
                        int andMask = Short.toUnsignedInt(req.andMask());
                        int orMask = Short.toUnsignedInt(req.orMask());
                        int result = (current & andMask) | (orMask & (~andMask));
                        writeSingleRegister.write(req.address(), (short) result);
                    },
                    readHoldingRegisters == null || writeMultipleRegisters == null ? null : req -> {
                        writeMultipleRegisters.write(req.writeAddress(), req.writeValue());
                        return new ReadWriteMultipleRegistersResponse(readHoldingRegisters.read(req.readAddress(), req.readQuantity()));
                    }
            );
        }
    }

}
