package com.github.h1m3k0.common.bytes;

import io.netty.buffer.ByteBuf;

import java.util.Iterator;

public abstract class ByteArray implements Iterable<Byte> {
    protected final int offset;
    protected final int length;

    protected ByteArray(int offset, int length) {
        this.offset = offset;
        this.length = length;
    }

    /**
     * 需要重写的get方法 因为参数是真实索引 所以不能暴露
     */
    protected abstract byte get(int realIndex);

    /**
     * 需要重写的set方法 因为参数是真实索引 所以不能暴露
     */
    protected abstract void set(int realIndex, byte value);

    public int length() {
        return this.length;
    }

    public interface IndexData {
        byte get();

        void set(byte value);
    }

    /**
     * 参数为表面的索引 可循环 自动根据offset计算真实索引
     */
    public final IndexData index(int index) {
        index %= this.length;
        if (index < 0) {
            index += this.length;
        }
        int realIndex = this.offset + index;
        return new IndexData() {
            @Override
            public byte get() {
                return ByteArray.this.get(realIndex);
            }

            @Override
            public void set(byte value) {
                ByteArray.this.set(realIndex, value);
            }
        };
    }


    @Override
    public Iterator<Byte> iterator() {
        return new Iterator<Byte>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < ByteArray.this.length;
            }

            @Override
            public Byte next() {
                return ByteArray.this.index(index++).get();
            }
        };
    }

    public static ByteArray array(byte[] array, int offset, int length) {
        return new ByteArray(offset, length) {
            @Override
            public byte get(int realIndex) {
                return array[realIndex];
            }

            @Override
            public void set(int realIndex, byte value) {
                array[realIndex] = value;
            }
        };
    }

    public static ByteArray array(byte[] array) {
        return array(array, 0, array.length);
    }

    public static ByteArray byteBuf(ByteBuf buf, int offset, int length) {
        return new ByteArray(offset, length) {
            @Override
            public byte get(int realIndex) {
                return buf.getByte(realIndex);
            }

            public void set(int realIndex, byte value) {
                buf.setByte(realIndex, value);
            }
        };
    }

    public static ByteArray byteBuf(ByteBuf buf) {
        return byteBuf(buf, buf.readerIndex(), buf.readableBytes());
    }
}
