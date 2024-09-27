package test;


import com.github.h1m3k0.common.netty.decoder.HeadLengthDecoder;
import com.github.h1m3k0.common.netty.decoder.HeadLengthDecoderFactory;
import com.github.h1m3k0.common.netty.transfer.IntTransfer;
import com.github.h1m3k0.modbus.client.ModbusClient;
import com.github.h1m3k0.modbus.client.ModbusClientPool;
import com.github.h1m3k0.modbus.client.ModbusConfig;
import com.github.h1m3k0.common.bytes.DataType;
import com.github.h1m3k0.modbus.client.service.ModbusNode;
import com.github.h1m3k0.modbus.core.ModbusException;
import com.github.h1m3k0.modbus.core.enums.DataModel;
import com.github.h1m3k0.modbus.core.request.ReadHoldingRegistersRequest;

import java.util.ArrayList;
import java.util.List;

public class ClientDemo {

    public static void main(String[] args) throws Exception {
        ModbusClientPool pool = new ModbusClientPool(() -> HeadLengthDecoderFactory.builder(
                new Byte[]{null, null, 0, 0},
                IntTransfer.buildDefault16()).build());
        try {
            ModbusClient[] clients = new ModbusClient[2];
            for (int i = 0; i < 2; i++) {
                ModbusClient client = pool.newClient(new ModbusConfig("127.0.0.1").slaveId((byte) 1));
                client.connect().await();
                clients[i] = client;
            }
            for (int iii = 0; iii < 2000000; iii++) {
                try {
                    List<ModbusNode> nodeList = new ArrayList<>();
                    int address = 0;
                    for (int i = 0; i < 10; i++) {
                        ModbusNode node = new ModbusNode();
                        node.dataModel(DataModel.HoldingRegisters);
                        node.address(address);
                        node.dataType(DataType.int32);
                        nodeList.add(node);
                        address += 4;
                    }
                    clients[iii % 2].sendAsync(new ReadHoldingRegistersRequest(10, 10)).thenAccept(System.out::println);
//                    client.service().query(nodeList);
//                    for (ModbusNode node : nodeList) {
//                        System.out.println(node);
//                    }
                } catch (ModbusException e) {
                    e.printStackTrace();
                }
//                Thread.sleep(1000);
            }
            pool.shutdown();
        } catch (Exception ex) {
            if (ex instanceof ModbusException) {
                ModbusException e = (ModbusException) ex;

                System.out.println(e.reason());
                if (e.errorMessage() != null) {
                    System.out.println(e.errorMessage());
                    System.out.println(e.errorMessage().code());
                    System.out.println(e.errorMessage().msgCount());
                    System.out.println(e.errorMessage().slaveId());
                }
                pool.shutdown();
            } else {
                ex.printStackTrace();
            }
        }
    }
}
