package com.github.h1m3k0.common.bytes;

public class Byte4Number extends ByteNumber<Byte4Number> {
    public Byte4Number(int number) {
        super(new byte[]{
                (byte) ((number & 0xff000000) >> 0x18),
                (byte) ((number & 0xff0000) >> 0x10),
                (byte) ((number & 0xff00) >> 0x08),
                (byte) (number & 0xff),
        });
    }

    public Byte4Number(float number) {
        this(Float.floatToRawIntBits(number));
    }

    public Byte4Number(byte[] value) {
        super(value);
    }

    public Byte4Number(byte[] value, int offset) {
        super(value, offset, 4);
    }

    public Byte4Number(String hex) {
        super((hex = hex.replaceAll(" ", "")).length() == 8 ? hex : null);
    }

}
