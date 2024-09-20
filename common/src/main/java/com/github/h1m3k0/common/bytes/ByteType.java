package com.github.h1m3k0.common.bytes;

public enum ByteType {
    AB, BA,
    ABCD, CDAB, BADC, DCBA,
    ABCDEFGH, GHEFCDAB, BADCFEHG, HGFEDCBA,
    ;

    public static ByteType get(String name) {
        try {
            return ByteType.valueOf(name.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

    public static class Byte2 {
        public static final ByteType AB = ByteType.AB;
        public static final ByteType BA = ByteType.BA;
    }

    public static class Byte4 {
        public static final ByteType ABCD = ByteType.ABCD;
        public static final ByteType CDAB = ByteType.CDAB;
        public static final ByteType BADC = ByteType.BADC;
        public static final ByteType DCBA = ByteType.DCBA;
    }

    public static class Byte8 {
        public static final ByteType ABCDEFGH = ByteType.ABCDEFGH;
        public static final ByteType GHEFCDAB = ByteType.GHEFCDAB;
        public static final ByteType BADCFEHG = ByteType.BADCFEHG;
        public static final ByteType HGFEDCBA = ByteType.HGFEDCBA;
    }
}
