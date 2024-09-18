package com.github.h1m3k0.common.netty.decoder;

import com.github.h1m3k0.common.netty.transfer.BoolTransfer;
import com.github.h1m3k0.common.netty.utils.NettyUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

/**
 * 头尾校验解码器
 */
public class HeadTailDecoder extends HeadDecoder {
    /**
     * 上次尾帧位置
     */
    private static final AttributeKey<Integer> LastIndex = AttributeKey.valueOf("com.github.h1m3k0.netty.LastIndex-" + attribute_key_count);
    /**
     * 尾标识
     */
    private final Byte[] tail;
    /**
     * 校验器
     */
    private final BoolTransfer check;

    @Override
    protected boolean handle(ChannelHandlerContext ctx) throws Exception {
        if (!super.handle(ctx)) {
            return false;
        }
        ByteBuf buf = cache(ctx);
        ctx.channel().attr(LastIndex).setIfAbsent(head.length);
        while (true) {
            int tailIndex = NettyUtil.indexOf(buf.slice(ctx.channel().attr(LastIndex).get(), buf.readableBytes() - ctx.channel().attr(LastIndex).get()), tail);
            if (tailIndex == -1) {
                cache(ctx, buf);
                return false;
            }
            tailIndex += ctx.channel().attr(LastIndex).get();
            if (check != null) {
                if (check.getFunction().apply(buf.slice(0, check.getLength() > 0 ? check.getLength() : tailIndex + tail.length + check.getLength()))) {
                    send(ctx, buf.slice(0, tailIndex + tail.length));
                    cache(ctx, buf.slice(tailIndex + tail.length, buf.writerIndex() - tailIndex - tail.length));
                    ctx.channel().attr(LastIndex).set(null);
                    return true;
                } else {
                    ctx.channel().attr(LastIndex).set(tailIndex + 1);
                }
            } else {
                send(ctx, buf.slice(0, tailIndex + tail.length));
                cache(ctx, buf.slice(tailIndex + tail.length, buf.writerIndex() - tailIndex - tail.length));
            }
        }
    }

    protected HeadTailDecoder(Byte[] head, Byte[] tail, BoolTransfer check, Long maxTime, Integer maxLength) {
        super(head, maxTime, maxLength);
        this.tail = tail;
        this.check = check;
    }
}
