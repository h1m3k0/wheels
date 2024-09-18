package com.github.h1m3k0.common.netty.utils;

import io.netty.buffer.ByteBuf;

public class NettyUtil {
    private static int containsWithNull(Byte[] str, byte b) {
        for (int i = str.length - 1; i >= 0; i--) {
            if (str[i] == null || str[i] == b) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 查询标志位置
     *
     * @param buf    整体buf
     * @param target 标志
     * @return index
     */
    public static int indexOf(ByteBuf buf, Byte[] target) {
        if (buf == null) {
            return -1;
        }
        buf = buf.duplicate();
        int bufLength = buf.readableBytes();
        int readerIndex = buf.readerIndex();
        int targetLength = target.length;
        int i = 0, j = 0;
        while (i <= bufLength - targetLength + j) {
            if (target[j] != null && buf.getByte(i + readerIndex) != target[j]) {
                if (i == bufLength - targetLength + j) {
                    break;
                }
                int pos = containsWithNull(target, buf.getByte(i + readerIndex + targetLength - j));
                if (pos == -1) {
                    i = i + targetLength + 1 - j;
                } else {
                    i = i + targetLength - pos - j;
                }
                j = 0;
            } else {
                if (j == targetLength - 1) {
                    return i - j;
                } else {
                    i++;
                    j++;
                }
            }
        }
        return -1;
    }

    public static Byte[] toByteArray(byte[] bytes) {
        Byte[] array = new Byte[bytes.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = bytes[i];
        }
        return array;
    }
}
