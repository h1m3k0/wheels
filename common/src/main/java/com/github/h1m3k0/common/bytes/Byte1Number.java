package com.github.h1m3k0.common.bytes;

public class Byte1Number extends ByteNumber<Byte1Number> {
    public Byte1Number(byte number) {
        super(new byte[]{
                number
        });
    }

    public Byte1Number(byte[] value) {
        super(value);
    }

    public Byte1Number(byte[] value, int offset, int length) {
        super(value, offset, length);
    }

    public Byte1Number(String hex) {
        super(hex.length() == 2 ? hex : null);
    }
}
