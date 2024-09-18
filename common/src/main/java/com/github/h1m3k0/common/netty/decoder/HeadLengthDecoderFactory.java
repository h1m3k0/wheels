package com.github.h1m3k0.common.netty.decoder;

import com.github.h1m3k0.common.netty.transfer.IntTransfer;
import lombok.Data;
import lombok.experimental.Accessors;

public class HeadLengthDecoderFactory {
    public static Builder builder(Byte[] head, IntTransfer length) {
        return new Builder(head, length);
    }

    @Data
    @Accessors(chain = true, fluent = true)
    public static class Builder {
        private final Byte[] head;
        private final IntTransfer length;
        private Long maxTime;
        private Integer maxLength;

        private Builder(Byte[] head, IntTransfer length) {
            this.head = head;
            this.length = length;
        }

        public HeadLengthDecoder build() {
            return new HeadLengthDecoder(head, length, maxTime, maxLength);
        }
    }
}
