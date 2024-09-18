package com.github.h1m3k0.modbus.client.service;

import com.github.h1m3k0.common.bytes.ByteNumber;
import com.github.h1m3k0.modbus.client.ModbusClient;
import com.github.h1m3k0.modbus.core.ModbusException;
import com.github.h1m3k0.modbus.core.enums.DataModel;
import com.github.h1m3k0.modbus.core.request.ReadCoilsRequest;
import com.github.h1m3k0.modbus.core.request.ReadDiscreteInputsRequest;
import com.github.h1m3k0.modbus.core.request.ReadHoldingRegistersRequest;
import com.github.h1m3k0.modbus.core.request.ReadInputRegistersRequest;
import com.github.h1m3k0.modbus.core.response.ReadCoilsResponse;
import com.github.h1m3k0.modbus.core.response.ReadDiscreteInputsResponse;
import com.github.h1m3k0.modbus.core.response.ReadHoldingRegistersResponse;
import com.github.h1m3k0.modbus.core.response.ReadInputRegistersResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModbusClientUtil {
    public static void select(ModbusClient client, List<ModbusNode> nodes) throws ModbusException {
        Map<DataModel, List<ModbusNode>> nodeMap = nodes.stream().collect(Collectors.groupingBy(ModbusNode::dataModel));
        for (Map.Entry<DataModel, List<ModbusNode>> entry : nodeMap.entrySet()) {
            DataModel dataModel = entry.getKey();
            List<ModbusNode> nodeList = entry.getValue();
            nodeList.sort(Comparator.comparing(ModbusNode::address));
            List<List<ModbusNode>> lists = new ArrayList<>();
            {
                List<ModbusNode> list = new ArrayList<>();
                lists.add(list);
                ModbusNode node = nodeList.get(0);
                lists.get(0).add(node);
                ModbusNode lastNode = node;
                for (int i = 1; i < nodeList.size(); list.add(node), lastNode = node, i++) {
                    node = nodeList.get(i);
                    // 地址连续
                    if (lastNode.address() + lastNode.dataType().length() >= node.address()) {
                        // 不超过最大长度
                        if (node.address() - list.get(0).address() + node.dataType().length() < (0x7d << 1)) {
                            continue;
                        }
                    }
                    list = new ArrayList<>();
                    lists.add(list);
                }
            }
            for (List<ModbusNode> modbusNodeList : lists) {
                int address = modbusNodeList.get(0).address();
                int quantity = modbusNodeList.get(modbusNodeList.size() - 1).address()
                        - modbusNodeList.get(0).address()
                        + modbusNodeList.get(modbusNodeList.size() - 1).dataType().length();
                if (dataModel == DataModel.InputRegisters || dataModel == DataModel.HoldingRegisters) {
                    quantity /= 2;
                }
                byte[] values = null;
                switch (dataModel) {
                    case Coils: {
                        ReadCoilsResponse response = client.sendSync(new ReadCoilsRequest(address, quantity));
                        for (ModbusNode modbusNode : modbusNodeList) {
                            modbusNode.data(response.bits()[modbusNode.address() - modbusNodeList.get(0).address()]);
                        }
                    }
                    break;
                    case DiscreteInput: {
                        ReadDiscreteInputsResponse response = client.sendSync(new ReadDiscreteInputsRequest(address, quantity));
                        for (ModbusNode modbusNode : modbusNodeList) {
                            modbusNode.data(response.bits()[modbusNode.address() - modbusNodeList.get(0).address()]);
                        }
                    }
                    break;
                    case InputRegisters: {
                        ReadInputRegistersResponse response = client.sendSync(new ReadInputRegistersRequest(address, quantity));
                        values = response.value();
                    }
                    break;
                    case HoldingRegisters: {
                        ReadHoldingRegistersResponse response = client.sendSync(new ReadHoldingRegistersRequest(address, quantity));
                        values = response.value();
                    }
                    break;
                }
                // InputRegisters 或 HoldingRegisters
                if (values != null) {
                    for (ModbusNode node : modbusNodeList) {
                        ByteNumber<?> number = ByteNumber.create(values, node.address() - modbusNodeList.get(0).address(), node.dataType().length());
                        if (node.dataSorted() != null) {
                            number.type(node.dataSorted());
                        }
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
                        }
                    }
                }
            }
        }
    }
}
