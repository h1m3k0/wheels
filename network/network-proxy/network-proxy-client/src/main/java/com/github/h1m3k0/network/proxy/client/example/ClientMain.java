package com.github.h1m3k0.network.proxy.client.example;

import com.github.h1m3k0.network.proxy.client.BossClient;
import com.github.h1m3k0.network.proxy.client.BossClientPool;
import com.github.h1m3k0.network.proxy.client.BossConfig;

public class ClientMain {
    public static void main(String[] args) {
        try {
            BossClientPool pool = new BossClientPool();
            BossClient client1 = pool.newClient(new BossConfig("localhost", 12300, "localhost", 12312, 2404));
            client1.connect();
//            BossClient client2 = pool.newClient(new BossConfig("localhost", 12300, "localhost", 2404, 12313));
//            client2.connect();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

    }
}
