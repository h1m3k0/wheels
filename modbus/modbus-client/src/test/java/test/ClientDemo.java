package test;


import com.github.h1m3k0.modbus.client.ModbusClient;
import com.github.h1m3k0.modbus.client.ModbusClientPool;
import com.github.h1m3k0.modbus.client.ModbusConfig;
import com.github.h1m3k0.modbus.client.service.DataType;
import com.github.h1m3k0.modbus.client.service.ModbusClientUtil;
import com.github.h1m3k0.modbus.client.service.ModbusNode;
import com.github.h1m3k0.modbus.core.ModbusException;
import com.github.h1m3k0.modbus.core.enums.DataModel;

import java.util.ArrayList;
import java.util.List;

public class ClientDemo {

    public static void main(String[] args) throws Exception {
        ModbusClientPool pool = new ModbusClientPool();
        try {
            ModbusClient client = pool.newClient(new ModbusConfig("127.0.0.1").slaveId((byte) 1));
            client.connect().await();
            List<ModbusNode> nodeList = new ArrayList<>();
            int address = 0;
            for (int i = 0; i < 500; i++) {
                ModbusNode node = new ModbusNode();
                node.dataModel(DataModel.HoldingRegisters);
                node.address(address);
                node.dataType(DataType.i32);
                nodeList.add(node);
                address += 4;
            }
            ModbusClientUtil.select(client, nodeList);
            for (ModbusNode node : nodeList) {
                System.out.println(node);
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
                pool.shutdown();
            } else {
                ex.printStackTrace();
            }
        }
    }
}
