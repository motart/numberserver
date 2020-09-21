package com.newrelic.codingchallenge;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Integration test for the NumberServer
 * This requires a server to be running on localhost and port 4000
 */
class MainTest {

    public static void main(String[] args) {
        //get the localhost IP address, if server is running on some other IP, you need to use that
        final ExecutorService clientExecutorService = Executors.newFixedThreadPool(10);
        clientExecutorService.execute(startClient());
        clientExecutorService.execute(startClient());
        clientExecutorService.execute(startClient());
        clientExecutorService.execute(startClient());
        clientExecutorService.execute(startClient());
        clientExecutorService.shutdownNow();
    }

    private static Runnable startClient() {
        return () -> {
            ClientTest client = new ClientTest();
            try {
                client.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}

