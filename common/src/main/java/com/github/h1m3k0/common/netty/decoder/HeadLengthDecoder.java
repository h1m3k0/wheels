package com.github.h1m3k0.common.netty.decoder;

import com.github.h1m3k0.common.netty.transfer.IntTransfer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

/**
 * 头长解码器
 */
public class HeadLengthDecoder extends HeadDecoder {
    /**
     * 数据长度
     */
    private static final AttributeKey<Integer> DataLength = AttributeKey.valueOf("com.github.h1m3k0.netty.DataLength-" + attribute_key_count);
    /**
     * 长度解析器
     */
    private final IntTransfer lengthTransfer;

    @Override
    protected boolean handle(ChannelHandlerContext ctx) throws Exception {
        if (!super.handle(ctx)) {
            return false;
        }
        ByteBuf buf = cache(ctx);
        if (ctx.channel().attr(DataLength).get() == null) {
            if (buf.resetReaderIndex().readableBytes() >= head.length + lengthTransfer.getLength()) {
                ctx.channel().attr(DataLength).set(lengthTransfer.getFunction().apply(buf.slice(head.length, lengthTransfer.getLength())));
            } else {
                return false;
            }
        }
        if (buf.resetReaderIndex().readableBytes() >= head.length + lengthTransfer.getLength() + ctx.channel().attr(DataLength).get()) {
            send(ctx, buf.slice(0, head.length + lengthTransfer.getLength() + ctx.channel().attr(DataLength).get()));
            cache(ctx, buf.slice(head.length + lengthTransfer.getLength() + ctx.channel().attr(DataLength).get(), buf.resetReaderIndex().readableBytes() - head.length - lengthTransfer.getLength() - ctx.channel().attr(DataLength).get()));
            ctx.channel().attr(DataLength).set(null);
            return true;
        }
        return false;
    }

    protected HeadLengthDecoder(Byte[] head, IntTransfer lengthTransfer, Long maxTime, Integer maxLength) {
        super(head, maxTime, maxLength);
        this.lengthTransfer = lengthTransfer;
    }
}
