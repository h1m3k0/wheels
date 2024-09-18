package com.github.h1m3k0.modbus.core;

import com.github.h1m3k0.modbus.core.function.ModbusFunction;
import com.github.h1m3k0.modbus.core.errorres.ModbusError;
import com.github.h1m3k0.modbus.core.request.ModbusRequest;
import com.github.h1m3k0.modbus.core.response.ModbusResponse;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true, fluent = true)
public abstract class ModbusMessage<
        modbus extends ModbusMessage<modbus, function, request, response, error>,
        function extends ModbusFunction<function, request, response, error>,
        request extends ModbusRequest<function, request, response, error>,
        response extends ModbusResponse<function, request, response, error>,
        error extends ModbusError<function, request, response, error>
        > {
    protected int msgCount;
    protected byte slaveId;
    protected function function;

    /**
     * 直接获取数字功能码
     */
    public abstract byte code();

    public modbus msgCount(int msgCount) {
        this.msgCount = msgCount;
        return (modbus) this;
    }

    /**
     * byte slaveId
     */
    public modbus slaveId(int slaveId) {
        return slaveId((byte) slaveId);
    }

    public modbus slaveId(byte slaveId) {
        this.slaveId = slaveId;
        return (modbus) this;
    }

    public modbus function(function function) {
        this.function = function;
        return (modbus) this;
    }
}













