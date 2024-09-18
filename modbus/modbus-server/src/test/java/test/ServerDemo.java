package test;

import com.github.h1m3k0.modbus.core.response.ReadCoilsResponse;
import com.github.h1m3k0.modbus.core.response.ReadDiscreteInputsResponse;
import com.github.h1m3k0.modbus.core.response.ReadHoldingRegistersResponse;
import com.github.h1m3k0.modbus.core.response.ReadInputRegistersResponse;
import com.github.h1m3k0.modbus.server.ModbusServer;
import com.github.h1m3k0.modbus.server.ModbusServerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 打包Server
 * 将ServerDemo复制到Sources Root
 * 复制build.xml到pom.xml中
 * 启动: "java -jar modbus-server.jar 端口号"
 * 默认端口号 502
 */
public class ServerDemo {
    // 为方便DiscreteInput和InputRegister数据配置, 令
    //   Coil 和 DiscreteInput 同数据
    //   HoldingRegister 和 InputRegister 同数据
    public static void main(String[] args) throws Exception {
        Map<Integer, Boolean> map1 = new HashMap<>();
        Map<Integer, Short> map2 = new HashMap<>();
        int port = 502;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        ModbusServer modbusServer = ModbusServerFactory.builder(port)
                .setReadCoils(request -> {
                    boolean[] booleans = new boolean[request.quantity()];
                    for (int i = 0; i < request.quantity(); i++) {
                        if (map1.containsKey(i + request.address())) {
                            booleans[i - request.address()] = map1.get(i);
                        }
                    }
                    return new ReadCoilsResponse(booleans);
                })
                .setReadDiscreteInputs(request -> {
                    boolean[] booleans = new boolean[request.quantity()];
                    for (int i = 0; i < request.quantity(); i++) {
                        if (map1.containsKey(i + request.address())) {
                            booleans[i] = map1.get(i);
                        }
                    }
                    return new ReadDiscreteInputsResponse(booleans);
                })
                .setReadHoldingRegisters(request -> {
                    byte[] bytes = new byte[request.quantity() << 1];
                    for (int i = 0; i < request.quantity(); i++) {
                        if (map2.containsKey(request.address() + i)) {
                            bytes[i * 2] = (byte) ((map2.get(request.address() + i) & 0xFF00) >>> 8);
                            bytes[i * 2 + 1] = (byte) (map2.get(request.address() + i) & 0x00FF);
                        }
                    }
                    return new ReadHoldingRegistersResponse(bytes);
                })
                .setReadInputRegisters(request -> {
                    byte[] bytes = new byte[request.quantity() << 1];
                    for (int i = 0; i < request.quantity(); i++) {
                        if (map2.containsKey(request.address() + i)) {
                            bytes[i * 2] = (byte) ((map2.get(request.address() + i) & 0xFF00) >>> 8);
                            bytes[i * 2 + 1] = (byte) (map2.get(request.address() + i) & 0x00FF);
                        }
                    }
                    return new ReadInputRegistersResponse(bytes);
                })
                .setWriteSingleCoil(request -> {
                    map1.put(request.address(), request.value().boolValue());
                })
                .setWriteSingleRegister(request -> {
                    map2.put(request.address(), request.value());
                })
                .setWriteMultipleCoils(request -> {
                    for (int i = 0; i < request.bits().length; i++) {
                        map1.put(request.address() + i, request.bits()[i]);
                    }
                })
                .setWriteMultipleRegisters(request -> {
                    for (int i = 0; i < request.value().length; i += 2) {
                        map2.put(request.address() + i / 2, (short) (((request.value()[i] << 8) & 0xFF00) | (request.value()[i + 1] & 0x00FF)));
                    }
                })
                .build();
        modbusServer.init();
    }
}