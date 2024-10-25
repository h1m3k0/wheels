package test;

import com.github.h1m3k0.network.proxy.client.BossClient;

public class ClientDemo {
    public static void main(String[] args) throws Exception {
        BossClient bossClient = new BossClient("127.0.0.1",12300);
        bossClient.register("127.0.0.1", 12304, 12303);
        bossClient.register("127.0.0.1", 12302, 12301);
    }
}
