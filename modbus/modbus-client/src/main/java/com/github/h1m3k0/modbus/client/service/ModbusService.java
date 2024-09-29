package com.github.h1m3k0.modbus.client.service;

import com.github.h1m3k0.common.bytes.ByteArray;
import com.github.h1m3k0.common.bytes.ByteNumber;
import com.github.h1m3k0.modbus.client.ModbusClient;
import com.github.h1m3k0.modbus.core.ModbusException;
import com.github.h1m3k0.modbus.core.enums.DataModel;
import com.github.h1m3k0.modbus.core.request.ReadCoilsRequest;
import com.github.h1m3k0.modbus.core.request.ReadDiscreteInputsRequest;
import com.github.h1m3k0.modbus.core.request.ReadHoldingRegistersRequest;
import com.github.h1m3k0.modbus.core.request.ReadInputRegistersRequest;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 一些实际应用时相关的
 */
@AllArgsConstructor
public class ModbusService {
    private final ModbusClient client;

    public void query(Collection<ModbusNode> nodes) throws ModbusException {
        Map<DataModel, List<ModbusNode>> nodeMap = nodes.stream().collect(Collectors.groupingBy(ModbusNode::dataModel));
        for (Map.Entry<DataModel, List<ModbusNode>> entry : nodeMap.entrySet()) {
            DataModel dataModel = entry.getKey();
            List<ModbusNode> nodeList = entry.getValue();
            nodeList.sort(Comparator.comparing(ModbusNode::address));
            List<List<ModbusNode>> lists = new ArrayList<>();
            {
                List<ModbusNode> list;
                ModbusNode lastNode;
                lists.add(list = new ArrayList<>());
                list.add(lastNode = nodeList.remove(0));
                for (ModbusNode node : nodeList) {
                    // 地址不连续
                    if ((lastNode.address() + lastNode.dataType().length() < node.address())
                            // 超过最大长度
                            || (node.address() - list.get(0).address() + node.dataType().length() >= (0x7d << 1)))
                        lists.add(list = new ArrayList<>());
                    list.add(lastNode = node);
                }
            }
            for (List<ModbusNode> modbusNodeList : lists) {
                int address = modbusNodeList.get(0).address();
                int quantity = modbusNodeList.get(modbusNodeList.size() - 1).address()
                        - modbusNodeList.get(0).address()
                        + modbusNodeList.get(modbusNodeList.size() - 1).dataType().length();
                if (dataModel == DataModel.Coils || dataModel == DataModel.DiscreteInput) {
                    boolean[] bits = null;
                    if (dataModel == DataModel.Coils) {
                        bits = client.sendSync(new ReadCoilsRequest(address, quantity)).bits();
                    }
                    if (dataModel == DataModel.Coils) {
                        bits = client.sendSync(new ReadDiscreteInputsRequest(address, quantity)).bits();
                    }
                    assert bits != null;
                    for (ModbusNode node : modbusNodeList) {
                        node.data(bits[node.address() - modbusNodeList.get(0).address()]);
                    }
                }
                if (dataModel == DataModel.InputRegisters || dataModel == DataModel.HoldingRegisters) {
                    quantity /= 2;
                    byte[] values = null;
                    if (dataModel == DataModel.InputRegisters) {
                        values = client.sendSync(new ReadInputRegistersRequest(address, quantity)).value();
                    }
                    if (dataModel == DataModel.HoldingRegisters) {
                        values = client.sendSync(new ReadHoldingRegistersRequest(address, quantity)).value();
                    }
                    assert values != null;
                    for (ModbusNode node : modbusNodeList) {
                        ByteNumber<?> number = ByteNumber.create(new ByteArray(values, node.address() - modbusNodeList.get(0).address(), node.dataType().length()))
                                .type(node.byteType());
                        switch (node.dataType().type()) {
                            case UINT:
                                node.data(number.toUInt());
                                break;
                            case INT:
                                node.data(number.toInt());
                                break;
                            case FLOAT:
                                node.data(number.toFloat());
                                break;
                            case BIT:
                                node.data(number.toBit(Integer.parseInt(node.dataType().name().replaceAll("bit", ""))));
                                break;
                        }
                    }
                }
            }
        }
    }
}
