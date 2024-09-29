package com.github.h1m3k0.common.bytes;

import io.netty.buffer.ByteBuf;

import java.util.Iterator;

public class ByteArray implements Iterable<Byte> {
    private byte[] array;
    private ByteBuf buf;
    private final int offset;
    private final int length;

    public ByteArray(byte[] array) {
        this(array, 0, array.length);
    }

    public ByteArray(byte[] array, int offset, int length) {
        this.array = array;
        this.offset = offset;
        this.length = length;
    }

    public ByteArray(ByteBuf buf) {
        this(buf, buf.readerIndex(), buf.readableBytes());
    }

    public ByteArray(ByteBuf buf, int offset, int length) {
        this.buf = buf;
        this.offset = offset;
        this.length = length;
    }

    private int index(int i) {
        int index = i % length;
        if (index < 0) {
            index += length;
        }
        return offset + index;
    }

    public byte get(int index) {
        if (array != null) {
            return array[index(index)];
        }
        if (buf != null) {
            return buf.getByte(index(index));
        }
        throw new ArrayStoreException();
    }

    public Setter set(int index) {
        if (array != null) {
            return value -> array[index(index)] = value;
        }
        if (buf != null) {
            return value -> buf.setByte(index(index), value);
        }
        throw new ArrayStoreException();
    }

    public int length() {
        return this.length;
    }

    @Override
    public Iterator<Byte> iterator() {
        return new Iterator<Byte>() {
            private final int offset = ByteArray.this.offset;
            private final int length = ByteArray.this.length;
            private int index = 0;

            @Override
            public boolean hasNext() {
                return offset + length < length;
            }

            @Override
            public Byte next() {
                return array[offset + index++];
            }
        };
    }

    public interface Setter {
        void value(byte value);
    }
}
