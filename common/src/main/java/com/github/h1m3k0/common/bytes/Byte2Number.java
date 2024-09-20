package com.github.h1m3k0.common.bytes;

public class Byte2Number extends ByteNumber<Byte2Number> {
    public Byte2Number(short number) {
        super(new byte[]{
                (byte) ((byte) (number & 0xff00) >> 0x08),
                (byte) (number & 0xff)
        });
    }

    public Byte2Number(byte[] value) {
        super(value);
    }

    Byte2Number(byte[] value, int offset, int length) {
        super(value, offset, length);
    }

    @Override
    protected int length() {
        return 2;
    }

    public Byte2Number(String hex) {
        super(hex.length() == 4 ? hex : null);
    }

}
