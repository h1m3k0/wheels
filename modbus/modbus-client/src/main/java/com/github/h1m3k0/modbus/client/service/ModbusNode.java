package com.github.h1m3k0.modbus.client.service;

import com.github.h1m3k0.modbus.core.enums.DataModel;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true, fluent = true)
public class ModbusNode {
    private DataModel dataModel;
    private int address;
    private DataType dataType;
    private String dataSorted;
    private Object data;

}
