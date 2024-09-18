package com.github.h1m3k0.common.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

/**
 * 缓存错误信息
 */
@ChannelHandler.Sharable
public abstract class CachedDecoder extends ChannelInboundHandlerAdapter {
    protected static int attribute_key_count = 0;
    /**
     * 缓存的消息
     */
    private final AttributeKey<ByteBuf> CacheBuf = AttributeKey.valueOf("com.github.h1m3k0.netty.CacheBuf-" + attribute_key_count);
    /**
     * 上一次收到消息的时间
     */
    private final AttributeKey<Long> LastTime = AttributeKey.valueOf("com.github.h1m3k0.netty.LastTime-" + attribute_key_count);
    /**
     * 最大等待时间
     */
    protected final long maxTime;
    /**
     * 最大缓存长度
     */
    protected final int maxLength;

    /**
     * 核心方法
     * <p>
     * 单次处理
     * <p>
     * 消息接收由CacheBuf
     *
     * @param ctx ctx
     * @return 是否通过
     * <p>
     * true 通过
     * <p>
     * false 不通过
     * @throws Exception ex
     */
    protected abstract boolean handle(ChannelHandlerContext ctx) throws Exception;

    /**
     * 解决时间和超长问题
     * <p>
     * 执行核心方法前判断时间是否舍弃旧缓存
     * <p>
     * 执行核心方法后判断长度是否舍弃旧缓存
     *
     * @param ctx ctx
     * @param buf 读到的数据
     */
    protected void handleMessage(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        try {
            if (ctx.channel().attr(LastTime).getAndSet(System.currentTimeMillis()) - ctx.channel().attr(LastTime).get() < -maxTime && ctx.channel().attr(CacheBuf).get() != null) {
                ctx.channel().attr(CacheBuf).getAndSet(null).release();
            }
            if (ctx.channel().attr(CacheBuf).get() == null) {
                ctx.channel().attr(CacheBuf).set(buf);
            } else {
                ctx.channel().attr(CacheBuf).set(ByteBufAllocator.DEFAULT.compositeBuffer().addComponents(true, ctx.channel().attr(CacheBuf).get(), buf));
            }
            while (handle(ctx)) ;
        } finally {
            if (ctx.channel().attr(CacheBuf).get() != null && ctx.channel().attr(CacheBuf).get().writerIndex() > maxLength) {
                ctx.channel().attr(CacheBuf).getAndSet(null).release();
            }
        }
    }

    /**
     * 入站时设置LAST_TIME
     *
     * @param ctx ctx
     * @throws Exception ex
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().attr(LastTime).set(0L);
        super.channelActive(ctx);
    }

    /**
     * 出站时清空BUF
     *
     * @param ctx ctx
     * @throws Exception ex
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel().attr(CacheBuf).get() != null) {
            ctx.channel().attr(CacheBuf).getAndSet(null).release();
        }
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        handleMessage(ctx, (ByteBuf) msg);
    }

    /**
     * 完成匹配 发送报文
     */
    protected final void send(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        super.channelRead(ctx, buf.copy());
    }

    /**
     * 读取缓存报文
     */
    protected final ByteBuf cache(ChannelHandlerContext ctx) {
        ByteBuf cache = ctx.channel().attr(CacheBuf).get();
        if (cache != null) {
            return cache;
        } else {
            return ByteBufAllocator.DEFAULT.buffer();
        }
    }

    /**
     * 写入缓存报文
     */
    protected final void cache(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        if (buf != null) {
            ctx.channel().attr(CacheBuf).set(buf.copy());
        } else {
            ctx.channel().attr(CacheBuf).set(null);
        }
        ReferenceCountUtil.release(buf);
    }

    /**
     * @param maxTime   最大等待时间(ms)
     * @param maxLength 最大缓存长度(Byte)
     */
    protected CachedDecoder(Long maxTime, Integer maxLength) {
        this.maxTime = maxTime == null ? 10_000 : maxTime;
        this.maxLength = maxLength == null ? 2048 * 10 : maxLength;
        attribute_key_count++;
    }
}
