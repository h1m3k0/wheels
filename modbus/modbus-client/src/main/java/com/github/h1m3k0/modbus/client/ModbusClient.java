package com.github.h1m3k0.modbus.client;

import com.github.h1m3k0.common.netty.client.Client;
import com.github.h1m3k0.modbus.client.service.ModbusService;
import com.github.h1m3k0.modbus.core.ModbusException;
import com.github.h1m3k0.modbus.core.ModbusMessage;
import com.github.h1m3k0.modbus.core.enums.ExceptionCause;
import com.github.h1m3k0.modbus.core.errorres.ModbusError;
import com.github.h1m3k0.modbus.core.function.ModbusFunction;
import com.github.h1m3k0.modbus.core.request.ModbusRequest;
import com.github.h1m3k0.modbus.core.response.ModbusResponse;

import java.util.concurrent.*;

public class ModbusClient extends Client<ModbusConfig, ModbusClient, ModbusClientPool> {
    private final int maxNumber;
    private final Byte slaveId;
    private final long requestTimeout;
    private final long responseTimeout;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final ModbusService modbusService = new ModbusService(this);

    protected ModbusClient(ModbusClientPool pool, ModbusConfig config) {
        super(pool, config.host(), config.port());
        this.maxNumber = config.maxNumber();
        this.slaveId = config.slaveId();
        this.requestTimeout = config.requestTimeout();
        this.responseTimeout = config.responseTimeout();
    }


    private <function extends ModbusFunction<function, request, response, error>,
            request extends ModbusRequest<function, request, response, error>,
            response extends ModbusResponse<function, request, response, error>,
            error extends ModbusError<function, request, response, error>> int send(request request) throws ModbusException {
        if (!channel.isActive()) {
            throw new ModbusException(ExceptionCause.NotConnected);
        }
        long startTime = System.currentTimeMillis();
        Integer tempCount = channel.attr(AttributeKeys.MessageCount()).get();
        int msgCount = tempCount == null ? 0 : tempCount;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        while (true) {
            for (; msgCount < maxNumber; msgCount++) {
                if (channel.attr(AttributeKeys.Lock(msgCount)).setIfAbsent(countDownLatch) == null) {
                    channel.attr(AttributeKeys.MessageCount()).set(msgCount);
                    request.msgCount((short) msgCount);
                    if (slaveId != null) {
                        request.slaveId(slaveId);
                    }
                    channel.attr(AttributeKeys.Message(msgCount)).set(request);
                    channel.writeAndFlush(request);
                    return msgCount;
                }
            }
            if (System.currentTimeMillis() - startTime > this.requestTimeout) {
                throw new ModbusException(ExceptionCause.RequestTimeout);
            }
            msgCount = 0;
        }
    }

    private <function extends ModbusFunction<function, request, response, error>,
            request extends ModbusRequest<function, request, response, error>,
            response extends ModbusResponse<function, request, response, error>,
            error extends ModbusError<function, request, response, error>> response receive(int msgCount) throws ModbusException {
        try {
            if ((channel.attr(AttributeKeys.Lock(msgCount)).get()).await(this.responseTimeout, TimeUnit.MILLISECONDS)) {
                ModbusMessage<?, ?, ?, ?, ?> message = channel.attr(AttributeKeys.Message(msgCount)).get();
                if (message instanceof ModbusError) {
                    ModbusError<?, ?, ?, ?> error = (ModbusError<?, ?, ?, ?>) message;
                    throw new ModbusException(ExceptionCause.ErrorMessage, error);
                } else {
                    return (response) message;
                }
            } else {
                throw new ModbusException(ExceptionCause.ResponseTimeout);
            }
        } catch (InterruptedException e) {
            return null;
        } finally {
            channel.attr(AttributeKeys.Lock(msgCount)).set(null);
            channel.attr(AttributeKeys.Message(msgCount)).set(null);
        }
    }

    public <function extends ModbusFunction<function, request, response, error>,
            request extends ModbusRequest<function, request, response, error>,
            response extends ModbusResponse<function, request, response, error>,
            error extends ModbusError<function, request, response, error>> CompletableFuture<response> sendAsync(request request) throws ModbusException {
        int msgCount = send(request);
        CompletableFuture<response> future = new CompletableFuture<>();
        executorService.execute(() -> {
            try {
                future.complete(receive(msgCount));
            } catch (ModbusException e) {
                future.completeExceptionally(new ModbusException(e.reason(), e.errorMessage()));
            }
        });
        return future;
    }

    public <function extends ModbusFunction<function, request, response, error>,
            request extends ModbusRequest<function, request, response, error>,
            response extends ModbusResponse<function, request, response, error>,
            error extends ModbusError<function, request, response, error>> response sendSync(request request) throws ModbusException {
        return receive(send(request));
    }

    public ModbusService service() {
        return modbusService;
    }
}
