package com.github.h1m3k0.common.bytes;

public class Byte2Number extends ByteNumber<Byte2Number> {
    public Byte2Number(short number) {
        super(new ByteArray(new byte[]{
                (byte) ((number & 0xff00) >> 0x08),
                (byte) (number & 0xff)
        }));
    }

    public Byte2Number(ByteArray array) {
        super(array);
    }

    public Byte2Number(String hex) {
        super((hex = hex.replaceAll(" ", "")).length() == 4 ? hex : null);
    }

}
