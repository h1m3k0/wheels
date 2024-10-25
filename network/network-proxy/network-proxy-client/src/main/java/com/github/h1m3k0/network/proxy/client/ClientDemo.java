package com.github.h1m3k0.network.proxy.client;

public class ClientDemo {
    public static void main(String[] args) {
        ProxyClient proxyClient = new ProxyClient();
        proxyClient.connect("127.0.0.1", 12300, 12301);
    }
}
