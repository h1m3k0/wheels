package test;


import com.github.h1m3k0.common.bytes.DataType;
import com.github.h1m3k0.common.netty.decoder.HeadLengthDecoderFactory;
import com.github.h1m3k0.common.netty.transfer.IntTransfer;
import com.github.h1m3k0.modbus.client.ModbusClient;
import com.github.h1m3k0.modbus.client.ModbusClientPool;
import com.github.h1m3k0.modbus.client.ModbusConfig;
import com.github.h1m3k0.modbus.client.service.ModbusNode;
import com.github.h1m3k0.modbus.core.ModbusException;
import com.github.h1m3k0.modbus.core.enums.DataModel;

import java.util.ArrayList;
import java.util.List;

public class ClientDemo {

    public static void main(String[] args) throws Exception {
        try (ModbusClientPool pool = new ModbusClientPool(() -> HeadLengthDecoderFactory.builder(
                new Byte[]{null, null, 0, 0},
                IntTransfer.buildDefault16()).build())) {
            try {
                try (ModbusClient client = pool.newClient(new ModbusConfig("127.0.0.1"))) {
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
                    client.service().query(nodeList);
                    for (ModbusNode node : nodeList) {
                        System.out.println(node);
                    }
                } catch (ModbusException e) {
                    e.printStackTrace();
                }
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
                } else {
                    ex.printStackTrace();
                }
            }
        }
    }
}
