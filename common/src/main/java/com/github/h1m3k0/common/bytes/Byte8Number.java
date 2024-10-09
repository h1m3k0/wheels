package com.github.h1m3k0.common.bytes;

public class Byte8Number extends ByteNumber<Byte8Number> {
    public Byte8Number(long number) {
        super(ByteArray.array(new byte[]{
                (byte) ((number & 0xff00000000000000L) >> 0x38),
                (byte) ((number & 0xff000000000000L) >> 0x30),
                (byte) ((number & 0xff0000000000L) >> 0x28),
                (byte) ((number & 0xff00000000L) >> 0x20),
                (byte) ((number & 0xff000000L) >> 0x18),
                (byte) ((number & 0xff0000) >> 0x10),
                (byte) ((number & 0xff00) >> 0x8),
                (byte) (number & 0xff),
        }));
    }

    public Byte8Number(double number) {
        this(Double.doubleToLongBits(number));
    }

    public Byte8Number(ByteArray array) {
        super(array);
    }

    public Byte8Number(String hex) {
        super((hex = hex.replaceAll(" ", "")).length() == 16 ? hex : null);
    }

}
