package com.github.h1m3k0.modbus.core.utils;

public class ModbusUtil {
    public static byte[] bitsToBytes(boolean[] bits) {
        byte[] bytes = new byte[bits.length / 8 + ((bits.length % 8 == 0) ? 0 : 1)];
        for (int i = 0; i < bits.length; i++) {
            bytes[i / 8] += bits[i] ? (1 << (i % 8)) : 0;
        }
        return bytes;
    }

    public static boolean[] bytesToBits(byte[] bytes, int quantity) {
        boolean[] bits = new boolean[quantity];
        for (int i = 0; i < bits.length; i++) {
            bits[i] = (bytes[i / 8] & (1 << (i % 8))) != 0;
        }
        return bits;
    }

    /**
     * 将bytes转为short bytes.length需要等于2
     * <pre>
     * 如果length>2, 取前2位
     * 00 01 02 => 00 01 => 1(short)
     * 如果length<2, 后补零(虽然1位时前补零更方便, 但2位是取前, 需与2位时保持一致)
     * 01 => 01 00 => 128(short)
     * </pre>
     *
     * @param bytes 数组
     * @return short
     */
    public static short bytesToShort(byte[] bytes) {
        if (bytes.length > 1) {
            return (short) ((bytes[0] & 0xFF00) | (bytes[1] & 0x00FF));
        } else if (bytes.length == 1) {
            return (short) (bytes[0] & 0xFF00);
        } else {
            return 0;
        }
    }

    public static byte[] shortToBytes(short s) {
        return new byte[]{(byte) (s & 0xFF00), (byte) (s & 0x00FF)};
    }

}
