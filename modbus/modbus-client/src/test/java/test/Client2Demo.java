package test;

import com.github.h1m3k0.common.netty.decoder.HeadLengthDecoderFactory;
import com.github.h1m3k0.common.netty.transfer.IntTransfer;
import com.github.h1m3k0.modbus.client.ModbusClient;
import com.github.h1m3k0.modbus.client.ModbusClientPool;
import com.github.h1m3k0.modbus.client.ModbusConfig;
import com.github.h1m3k0.modbus.core.request.ReadHoldingRegistersRequest;

public class Client2Demo {
    public static void main(String[] args) {
        try (ModbusClientPool pool = new ModbusClientPool(
                HeadLengthDecoderFactory.builder(new Byte[]{null, null, 0, 0}, IntTransfer.buildDefault16()).build()
        );
             ModbusClient client = pool.newClient(new ModbusConfig("127.0.0.1").maxNumber(Short.MAX_VALUE).slaveId((byte) 1))) {

            client.connect().await();
            for (int i = 0; i < 1000000; i++) {
//                client.sendAsync(new ReadHoldingRegistersRequest(0, 10))
//                        .thenAccept(System.out::println);

                int finalI = i;
                client.sendAsync(new ReadHoldingRegistersRequest(0, 10)).whenComplete((res, err) -> {
                    if (res != null) {
//                        System.out.println(finalI + " " + res);
                    }
                    if (err != null) {
                        err.printStackTrace();
                    }
                });
            }
            Thread.sleep(20_000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
