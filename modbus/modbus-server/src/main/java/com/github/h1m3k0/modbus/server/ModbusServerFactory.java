package com.github.h1m3k0.modbus.server;


import com.github.h1m3k0.modbus.core.ModbusConsumer;
import com.github.h1m3k0.modbus.core.errorres.*;
import com.github.h1m3k0.modbus.core.function.*;
import com.github.h1m3k0.modbus.core.request.*;
import com.github.h1m3k0.modbus.core.response.*;
import com.github.h1m3k0.modbus.server.functional.ModbusFunctional;
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
        private ModbusFunctional<ReadDiscreteInputsFunction, ReadDiscreteInputsRequest, ReadDiscreteInputsResponse, ReadDiscreteInputsError> readDiscreteInputs;
        private ModbusFunctional<ReadHoldingRegistersFunction, ReadHoldingRegistersRequest, ReadHoldingRegistersResponse, ReadHoldingRegistersError> readHoldingRegisters;
        private ModbusFunctional<ReadInputRegistersFunction, ReadInputRegistersRequest, ReadInputRegistersResponse, ReadInputRegistersError> readInputRegisters;
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
            return new ModbusServer(port, readCoils, readDiscreteInputs, readHoldingRegisters, readInputRegisters, writeSingleCoil, writeSingleRegister, writeMultipleCoils, writeMultipleRegisters, maskWriteRegister, readWriteMultipleRegisters);
        }
    }
}
