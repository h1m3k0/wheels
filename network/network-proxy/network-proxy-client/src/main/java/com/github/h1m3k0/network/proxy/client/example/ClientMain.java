package com.github.h1m3k0.network.proxy.client.example;

import com.github.h1m3k0.network.proxy.client.BossClient;
import com.github.h1m3k0.network.proxy.client.BossClientPool;
import com.github.h1m3k0.network.proxy.client.BossConfig;

public class ClientMain {
    public static void main(String[] args) {
        try {
            BossClientPool pool = new BossClientPool();
            BossClient client1 = pool.newClient(new BossConfig("10.10.65.247", 12300, "10.160.82.12", 2404, 12312));
            client1.connect();
            BossClient client2 = pool.newClient(new BossConfig("10.10.65.247", 12300, "10.160.80.13", 2404, 12313));
            client2.connect();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

    }
}
