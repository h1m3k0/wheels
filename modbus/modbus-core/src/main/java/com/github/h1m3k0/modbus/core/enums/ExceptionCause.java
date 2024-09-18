package com.github.h1m3k0.modbus.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Accessors(chain = true, fluent = true)
public enum ExceptionCause {
    ErrorMessage("返回消息错误"),
    NotConnected("客户端未连接"),
    ResponseTimeout("等待响应超时"),
    RequestTimeout("等待请求超时"),
    ;
    private final String description;
}