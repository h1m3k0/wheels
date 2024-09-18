package com.github.h1m3k0.common.netty.decoder;

import com.github.h1m3k0.common.netty.transfer.BoolTransfer;
import lombok.Data;
import lombok.experimental.Accessors;

public class HeadTailDecoderFactory {
    public static Builder builder(Byte[] head, Byte[] tail) {
        return new Builder(head, tail);
    }

    @Data
    @Accessors(chain = true, fluent = true)
    public static class Builder {
        private final Byte[] head;
        private final Byte[] tail;
        private BoolTransfer check;
        private Long maxTime;
        private Integer maxLength;

        private Builder(Byte[] head, Byte[] tail) {
            this.head = head;
            this.tail = tail;
        }

        public HeadTailDecoder build() {
            return new HeadTailDecoder(head, tail, check, maxTime, maxLength);
        }

    }
}
