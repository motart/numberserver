package com.newrelic.codingchallenge;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class ClientTest {
    private static final String ADDR = "127.0.0.1";
    private static final int PORT = 4000;
    private static final int DATA_SIZE = 2000000;

    /**
     * Start a client connection to the Socket Server (localhost) at port 4000
     * and seed dataSize lines of randomly generated 9-digit strings
     * @throws IOException
     */
    void start() throws IOException {
        Random random = new Random();
        StringBuilder value = new StringBuilder();
        Socket socket = new Socket(ADDR, PORT);

        OutputStream out = socket.getOutputStream();
        for (int i = 1; i < DATA_SIZE; i++) {
            value.append(100000000 + random.nextInt(900000000) + "\n");
        }
        out.write(value.toString().getBytes());
        socket.close();
    }
}
