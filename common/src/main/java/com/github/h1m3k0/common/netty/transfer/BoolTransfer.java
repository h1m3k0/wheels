package com.github.h1m3k0.common.netty.transfer;

import io.netty.buffer.ByteBuf;

import java.util.function.Function;

/**
 * bool转换器
 * <p>
 * 将{@link ByteBuf}转换成{@link Boolean}
 */
public class BoolTransfer extends Transfer<Boolean> {

    private BoolTransfer(int length, Function<ByteBuf, Boolean> function) {
        super(length, function);
    }

    /**
     * 创建新对象, 转换结果为原结果的非
     * <p>
     * 不修改原始对象
     *
     * @return 新的BoolTransfer
     */
    public BoolTransfer not() {
        return new BoolTransfer(length, buf -> !function.apply(buf));
    }


    /**
     * 创建新对象, 放弃前waitLength个字节再转换
     *
     * @param waitLength 前面的字节数
     * @return 新的IntTransfer
     */
    public BoolTransfer wait(int waitLength) {
        return new BoolTransfer(length + waitLength, buf -> function.apply(buf.slice(waitLength, length)));
    }

    /**
     * 普通地构建转换器
     *
     * @param length   解析长度
     * @param function 解析方法
     * @return IntTransfer
     */
    public static BoolTransfer build(int length, Function<ByteBuf, Boolean> function) {
        return new BoolTransfer(length, function);
    }
}
