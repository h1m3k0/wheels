package com.github.h1m3k0.modbus.core.request;

import com.github.h1m3k0.modbus.core.errorres.UndefinedError;
import com.github.h1m3k0.modbus.core.function.UndefinedFunction;
import com.github.h1m3k0.modbus.core.response.UndefinedResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true, fluent = true)
public class UndefinedRequest extends ModbusRequest<UndefinedFunction, UndefinedRequest, UndefinedResponse, UndefinedError> {
}
