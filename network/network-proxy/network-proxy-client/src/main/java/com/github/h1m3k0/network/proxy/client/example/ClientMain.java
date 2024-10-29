package com.github.h1m3k0.network.proxy.client.example;

import com.github.h1m3k0.network.proxy.client.BossClient;
import com.github.h1m3k0.network.proxy.client.BossClientPool;
import com.github.h1m3k0.network.proxy.client.BossConfig;

public class ClientMain {
    public static void main(String[] args) {
        try {
            BossClientPool pool = new BossClientPool();
            BossClient client1 = pool.newClient(new BossConfig("localhost", 12300,
                    12312, 11223));
            client1.connect().sync();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

    }
}
