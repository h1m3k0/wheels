package com.github.h1m3k0.modbus.core.response;

import com.github.h1m3k0.modbus.core.errorres.UndefinedError;
import com.github.h1m3k0.modbus.core.function.UndefinedFunction;
import com.github.h1m3k0.modbus.core.request.UndefinedRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class UndefinedResponse extends ModbusResponse<UndefinedFunction, UndefinedRequest, UndefinedResponse, UndefinedError> {
}
