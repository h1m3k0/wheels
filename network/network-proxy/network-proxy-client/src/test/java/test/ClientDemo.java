package test;

import com.github.h1m3k0.network.proxy.client.BossClient;
import com.github.h1m3k0.network.proxy.client.BossClientPool;
import com.github.h1m3k0.network.proxy.client.BossConfig;

public class ClientDemo {
    public static void main(String[] args) throws Exception {
        try (BossClientPool pool = new BossClientPool()) {
            BossClient client1 = pool.newClient(new BossConfig("127.0.0.1", 12300, "localhost", 12304, 12303));
            client1.connect().await();
            BossClient client2 = pool.newClient(new BossConfig("127.0.0.1", 12300, "localhost", 12302, 12301));
            client2.connect().syncUninterruptibly();
            Thread.sleep(1000000000);
        } catch (Exception e) {
            e.printStackTrace(System.err);

        }
    }
}
