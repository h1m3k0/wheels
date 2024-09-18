package com.github.h1m3k0.common.bytes;

public class Byte8Number extends ByteNumber<Byte8Number> {
    public Byte8Number(long number) {
        super(new byte[]{
                (byte) ((number & 0xff00000000000000L) >> 0x38),
                (byte) ((number & 0xff000000000000L) >> 0x30),
                (byte) ((number & 0xff0000000000L) >> 0x28),
                (byte) ((number & 0xff00000000L) >> 0x20),
                (byte) ((number & 0xff000000L) >> 0x18),
                (byte) ((number & 0xff0000) >> 0x10),
                (byte) ((number & 0xff00) >> 0x8),
                (byte) (number & 0xff),
        });
    }

    public Byte8Number(double number) {
        this(Double.doubleToLongBits(number));
    }

    public Byte8Number(byte[] value) {
        super(value);
    }

    public Byte8Number(byte[] value, int offset, int length) {
        super(value, offset, length);
    }

    public Byte8Number(String hex) {
        super(hex.length() == 16 ? hex : null);
    }

    public enum Type {
        ABCDEFGH, GHEFCDAB, BADCFEHG, HGFEDCBA,
    }

    public Byte8Number type(Type type) {
        this.type = type.name();
        return this;
    }
}
