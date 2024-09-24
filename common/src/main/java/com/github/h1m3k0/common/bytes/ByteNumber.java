package com.github.h1m3k0.common.bytes;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(chain = true, fluent = true)
public abstract class ByteNumber<number extends ByteNumber<number>> {
    private int length;
    protected final byte[] value;
    protected final int offset;
    @Getter
    protected ByteType type;

    /**
     * 自定义ByteNumber子类, 如果不满足命名规则, 就无法获得正确的length属性(-1), 需要重写length()方法
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


    public number type(ByteType type) {
        this.type = type;
        return (number) this;
    }

    public static <number extends ByteNumber<number>> number create(byte[] bytes, int offset, int length) {
        switch (length) {
            case 8:
                return (number) new Byte8Number(bytes, offset, length);
            case 4:
                return (number) new Byte4Number(bytes, offset, length);
            case 2:
                return (number) new Byte2Number(bytes, offset, length);
            case 1:
                return (number) new Byte1Number(bytes, offset, length);
        }
        throw new NumberFormatException();
    }

    public static <number extends ByteNumber<number>> number create(byte[] bytes) {
        return create(bytes, 0, bytes.length);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (byte v : value) {
            builder.append(String.format("%02x", Byte.toUnsignedInt(v)));
        }
        return builder.toString();
    }

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

    public ByteNumber(byte[] bytes, int offset, int length) {
        this.value = bytes;
        this.offset = offset;
        if (length != this.length()) {
            throw new NumberFormatException();
        }
    }

    public ByteNumber(byte[] bytes) {
        this(bytes, 0, bytes.length);
    }

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

    public boolean toBit(int index) {
        if (this.type != null) {
            for (int i = 0; i < this.type.name().length(); i++) {
                if (this.type.name().charAt(i) - 'A' == index / 8) {
                    return (this.value[this.offset + i] & (1 << index)) != 0;
                }
            }
            throw new IllegalArgumentException();
        } else {
            return (this.value[this.offset + index / 8] & (1 << index)) != 0;
        }
    }
}
