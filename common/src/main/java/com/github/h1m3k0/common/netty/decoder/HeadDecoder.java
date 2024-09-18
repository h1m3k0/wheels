package com.github.h1m3k0.common.netty.decoder;

import com.github.h1m3k0.common.netty.utils.NettyUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 头解码器
 */
public abstract class HeadDecoder extends CachedDecoder {
    /**
     * 头标识
     */
    protected final Byte[] head;

    /**
     * 判断是否有头标志, 并截取CacheBuf头标志及之后内容并发送
     *
     * @param ctx ctx
     * @return 是否通过
     * @throws Exception ex
     */
    @Override
    protected boolean handle(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = cache(ctx);
        int headIndex = NettyUtil.indexOf(buf, head);
        if (headIndex != -1) {
            buf = buf.slice(headIndex, buf.resetReaderIndex().readableBytes() - headIndex);
        }
        cache(ctx, buf);
        return headIndex != -1;
    }

    protected HeadDecoder(Byte[] head, Long maxTime, Integer maxLength) {
        super(maxTime, maxLength);
        this.head = head;
    }
}
