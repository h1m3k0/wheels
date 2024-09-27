package com.github.h1m3k0.common.bytes;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(chain = true, fluent = true)
public abstract class ByteNumber<number extends ByteNumber<number>> {
    /**
     * 根据命名规则获取的默认长度
     * 自定义子类需要遵守命名规则 或者重写length()方法
     */
    private int length;

    /**
     * 当前长度
     * 默认为length 或者重写此方法
     */
    protected int length() {
        if (this.length == 0) {
            try {
                this.length = Integer.parseInt(this.getClass().getSimpleName().replaceAll("[^0-9]", ""));
            } catch (NumberFormatException e) {
                this.length = -1;
            }
        }
        return this.length;
    }

    /**
     * 原始数组
     */
    protected final byte[] value;
    /**
     * 起始位置
     */
    protected final int offset;
    /**
     * 字节顺序
     * 与当前数值的类型无关
     */
    @Getter
    protected ByteType type;

    public number type(ByteType type) {
        this.type = type;
        return (number) this;
    }

    /**
     * @param hex 16进制字符串
     */
    public ByteNumber(String hex) {
        this(new byte[hex.length() >>> 1]);
        for (int i = 0; i < value.length; i++) {
            if ('0' <= hex.charAt(i * 2) && hex.charAt(i * 2) <= '9') {
                value[i] |= (byte) ((byte) (hex.charAt(i * 2) - '0') << 4);
            } else if ('a' <= hex.charAt(i * 2) && hex.charAt(i * 2) <= 'z') {
                value[i] |= (byte) ((byte) (hex.charAt(i * 2) - 'a' + 0xa) << 4);
            } else if ('A' <= hex.charAt(i * 2) && hex.charAt(i * 2) <= 'Z') {
                value[i] |= (byte) ((byte) (hex.charAt(i * 2) - 'A' + 0xA) << 4);
            } else {
                throw new NumberFormatException();
            }
            if ('0' <= hex.charAt(i * 2 + 1) && hex.charAt(i * 2 + 1) <= '9') {
                value[i] |= (byte) (hex.charAt(i * 2 + 1) - '0');
            } else if ('a' <= hex.charAt(i * 2 + 1) && hex.charAt(i * 2 + 1) <= 'z') {
                value[i] |= (byte) (hex.charAt(i * 2 + 1) - 'a' + 0xa);
            } else if ('A' <= hex.charAt(i * 2 + 1) && hex.charAt(i * 2 + 1) <= 'Z') {
                value[i] |= (byte) (hex.charAt(i * 2 + 1) - 'A' + 0xA);
            } else {
                throw new NumberFormatException();
            }
        }
    }

    /**
     * @param bytes  原始数组
     * @param offset 起始为止
     * @param length 长度(用于判断)
     */
    public ByteNumber(byte[] bytes, int offset, int length) {
        this.value = bytes;
        this.offset = offset;
        if (length != this.length()) {
            throw new NumberFormatException();
        }
    }

    /**
     * @param bytes 原始数组 (默认这个数组只表示当前这一个number)
     */
    public ByteNumber(byte[] bytes) {
        this(bytes, 0, bytes.length);
    }


    /**
     * toString 默认16进制
     */
    @Override
    public String toString() {
        return toString(0x10);
    }

    /**
     * 不用空格分隔
     *
     * @param n 进制
     */
    public String toString(int n) {
        return toString(n, false);
    }

    /**
     * @param n      进制
     * @param pretty 是否用空格分隔
     */
    public String toString(int n, boolean pretty) {
        StringBuilder builder = new StringBuilder();
        String max = Integer.toString(Byte.toUnsignedInt((byte) -1), n);
        for (byte v : value) {
            String num = Integer.toString(Byte.toUnsignedInt(v), n);
            for (int i = 0; i < max.length() - num.length(); i++) {
                builder.append('0');
            }
            builder.append(num);
            if (pretty) {
                builder.append(' ');
            }
        }
        return builder.substring(0, builder.length() - (pretty ? 1 : 0));
    }


    /**
     * 转为无符整型
     */
    public long toUInt() {
        long num = 0;
        if (this.type != null) {
            for (int i = 0; i < this.type.name().length(); i++) {
                num <<= 8;
                num |= Byte.toUnsignedInt(this.value[this.offset + this.type.name().charAt(i) - 'A']);
            }
        } else {
            for (int i = 0; i < this.length(); i++) {
                num <<= 8;
                num |= Byte.toUnsignedInt(this.value[this.offset + i]);
            }
        }
        return num;
    }

    /**
     * 转为有符整型
     * 根据length()决定整型范围
     */
    public long toInt() {
        switch (this.length()) {
            case 1:
                return (byte) toUInt();
            case 2:
                return (short) toUInt();
            case 4:
                return (int) toUInt();
            default:
                return toUInt();
        }
    }

    /**
     * 转为浮点数
     * 根据length()决定单精度/双精度
     */
    public double toFloat() {
        switch (this.length()) {
            case 4:
                return Float.intBitsToFloat((int) toInt());
            case 8:
                return Double.longBitsToDouble(toInt());
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * 从右向左数 获取某位的布尔型
     *
     * @param index 位
     */
    public boolean toBit(int index) {
        if (this.type != null) {
            for (int i = 0; i < this.type.name().length(); i++) {
                if (this.type.name().charAt(i) - 'A' == index / 8) {
                    return (this.value[this.value.length - this.offset - i - 1] & (1 << (index % 8))) != 0;
                }
            }
            throw new IllegalArgumentException();
        } else {
            return (this.value[this.value.length - this.offset - index / 8 - 1] & (1 << (index % 8))) != 0;
        }
    }

    /**
     * 几位组合成的无符整型
     *
     * <p>例如:
     * <p>对于<code>Byte1Number</code>而言, <code>toInt([0, 1, 2, 3, 4, 5, 6, 7])</code> 与 <code>toInt()</code> 相同
     * <p>对于<code>Byte2Number</code>而言, <code>toInt([0 ~ 15])</code> 与 <code>toInt()</code> 相同
     * <p>对于<code>Byte4Number</code>而言, <code>toInt([0 ~ 31])</code> 与 <code>toInt()</code> 相同
     * <p>对于<code>Byte8Number</code>而言, <code>toInt([0 ~ 63])</code> 与 <code>toInt()</code> 相同
     */
    public long toInt(int... indexes) {
        switch (indexes.length / 8) {
            case 1:
                return (byte) toUInt(indexes);
            case 2:
                return (short) toUInt(indexes);
            case 4:
                return (int) toUInt(indexes);
            default:
                return toUInt(indexes);
        }
    }

    /**
     * 几位组合成的无符整型
     *
     * <p>例如:
     * <p>对于<code>Byte1Number</code>而言, <code>toUInt([0, 1, 2, 3, 4, 5, 6, 7])</code> 与 <code>toUInt()</code> 相同
     * <p>对于<code>Byte2Number</code>而言, <code>toUInt([0 ~ 15])</code> 与 <code>toUInt()</code> 相同
     * <p>对于<code>Byte4Number</code>而言, <code>toUInt([0 ~ 31])</code> 与 <code>toUInt()</code> 相同
     * <p>对于<code>Byte8Number</code>而言, <code>toUInt([0 ~ 63])</code> 与 <code>toUInt()</code> 相同
     */
    public long toUInt(int... indexes) {
        long result = 0;
        for (int i = 0; i < indexes.length; i++) {
            result <<= 1;
            if (toBit(indexes[indexes.length - i - 1])) {
                result |= 1;
            }
        }
        return result;
    }


    public static <number extends ByteNumber<number>> number create(byte[] bytes, int offset, int length) {
        switch (length) {
            case 8:
                return (number) new Byte8Number(bytes, offset);
            case 4:
                return (number) new Byte4Number(bytes, offset);
            case 2:
                return (number) new Byte2Number(bytes, offset);
            case 1:
                return (number) new Byte1Number(bytes, offset);
        }
        throw new NumberFormatException();
    }

    public static <number extends ByteNumber<number>> number create(byte[] bytes) {
        return create(bytes, 0, bytes.length);
    }
}
