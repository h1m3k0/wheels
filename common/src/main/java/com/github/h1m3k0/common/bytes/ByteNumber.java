package com.github.h1m3k0.common.bytes;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(chain = true, fluent = true)
public abstract class ByteNumber<number extends ByteNumber<number>> {
    protected static final String[] hex = {
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0a", "0b", "0c", "0d", "0e", "0f",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "1a", "1b", "1c", "1d", "1e", "1f",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "2a", "2b", "2c", "2d", "2e", "2f",
            "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3a", "3b", "3c", "3d", "3e", "3f",
            "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "4a", "4b", "4c", "4d", "4e", "4f",
            "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "5a", "5b", "5c", "5d", "5e", "5f",
            "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "6a", "6b", "6c", "6d", "6e", "6f",
            "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "7a", "7b", "7c", "7d", "7e", "7f",
            "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "8a", "8b", "8c", "8d", "8e", "8f",
            "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "9a", "9b", "9c", "9d", "9e", "9f",
            "a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "aa", "ab", "ac", "ad", "ae", "af",
            "b0", "b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9", "ba", "bb", "bc", "bd", "be", "bf",
            "c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "ca", "cb", "cc", "cd", "ce", "cf",
            "d0", "d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "da", "db", "dc", "dd", "de", "df",
            "e0", "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9", "ea", "eb", "ec", "ed", "ee", "ef",
            "f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "fa", "fb", "fc", "fd", "fe", "ff",
    };
    protected final byte[] value;
    protected final int offset;
    protected final int length;
    @Getter
    protected String type;

    public number type(String type) {
        this.type = type.toUpperCase();
        return (number) this;
    }

    public static ByteNumber<?> create(byte[] bytes, int offset, int length) {
        switch (length) {
            case 8:
                return new Byte8Number(bytes, offset, length);
            case 4:
                return new Byte4Number(bytes, offset, length);
            case 2:
                return new Byte2Number(bytes, offset, length);
            case 1:
                return new Byte1Number(bytes, offset, length);
        }
        throw new NumberFormatException();
    }

    public static ByteNumber<?> create(byte[] bytes) {
        return create(bytes, 0, bytes.length);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (byte v : value) {
            builder.append(hex[Byte.toUnsignedInt(v)]);
        }
        return builder.toString();
    }

    public ByteNumber(String hex) {
        if (hex == null) {
            throw new NumberFormatException();
        }
        value = new byte[hex.length() >>> 1];
        for (int i = 0; i < value.length; i++) {
            if ('0' <= hex.charAt(i * 2) && hex.charAt(i * 2) <= '9') {
                value[i] |= (byte) ((byte) (hex.charAt(i * 2) - '0') << 4);
            } else if ('a' <= hex.charAt(i * 2) && hex.charAt(i * 2) <= 'z') {
                value[i] |= (byte) ((byte) (hex.charAt(i * 2) - 'a' + 10) << 4);
            } else if ('A' <= hex.charAt(i * 2) && hex.charAt(i * 2) <= 'Z') {
                value[i] |= (byte) ((byte) (hex.charAt(i * 2) - 'A' + 10) << 4);
            } else {
                throw new NumberFormatException();
            }
            if ('0' <= hex.charAt(i * 2 + 1) && hex.charAt(i * 2 + 1) <= '9') {
                value[i] |= (byte) (hex.charAt(i * 2 + 1) - '0');
            } else if ('a' <= hex.charAt(i * 2 + 1) && hex.charAt(i * 2 + 1) <= 'z') {
                value[i] |= (byte) (hex.charAt(i * 2 + 1) - 'a' + 10);
            } else if ('A' <= hex.charAt(i * 2 + 1) && hex.charAt(i * 2 + 1) <= 'Z') {
                value[i] |= (byte) (hex.charAt(i * 2 + 1) - 'A' + 10);
            } else {
                throw new NumberFormatException();
            }
        }
        offset = 0;
        length = value.length;
    }

    public ByteNumber(byte[] bytes, int offset, int length) {
        this.value = bytes;
        this.offset = offset;
        this.length = length;
    }

    public ByteNumber(byte[] bytes) {
        this(bytes, 0, bytes.length);
    }

    public long toInt() {
        switch (this.length) {
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
            for (int i = 0; i < this.type.length(); i++) {
                num <<= 8;
                num |= Byte.toUnsignedInt(this.value[this.offset + this.type.charAt(i) - 'A']);
            }
        } else {
            for (int i = 0; i < this.length; i++) {
                num <<= 8;
                num |= Byte.toUnsignedInt(this.value[this.offset + i]);
            }
        }
        return num;
    }

    public double toFloat() {
        switch (this.length) {
            case 4:
                return Float.intBitsToFloat((int) toInt());
            case 8:
                return Double.longBitsToDouble(toInt());
            default:
                return Double.NaN;
        }
    }
}
