package com.newrelic.codingchallenge;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.exit;

/**
 * A server that handles incoming numbers or control text from socket connection(s)
 */
@SuppressWarnings("deprecation")
public class NumberServer {
    public static final int MAX_CLIENTS = 5;
    public static final int MAX_STRING_LENGTH = 9;
    private final ExecutorService clientExecutorService = Executors.newFixedThreadPool(MAX_CLIENTS);
    private final Integer port;
    private final String fileName;
    private boolean stopped = false;
    private ServerSocket serverSocket;
    private LogFileWriter logFileWriter;
    private ReportPrinter reportPrinter = new ReportPrinter();
    private Hashtable<String, Integer> cache = new Hashtable<>();

    /**
     * Constructor
     *
     * @param port     server port number
     * @param fileName file name where to write client inputs
     */
    NumberServer(Integer port, String fileName) {
        this.port = port;
        this.fileName = fileName;
        this.logFileWriter = new LogFileWriter(fileName);
    }

    // Default constructor, for Mockito testing only
    NumberServer() {
        this.port = 4000;
        this.fileName = "";
    }

    /**
     * Starting point for each client connection.
     *
     * @throws IOException
     */
    public void start() throws IOException {
        setup();
        serverSocket = new ServerSocket(this.port);

        // Continually accept connections from the ServerSocket
        // The clientExecutorService is a fixedThreadPool which handles keeping only MAX_CLIENTS connections open
        // Prevent creating new threads when the existing connection count is  equal to max_clients
        while (!this.stopped) {
            clientExecutorService.execute(socketInputHandler(serverSocket.accept()));
        }
    }

    /**
     * Initial setup
     * @throws IOException
     */
    private void setup() throws IOException {
        logFileWriter.setupFile();
        reportPrinter.startAsync();
    }

    /**
     * Tear down
     * @throws IOException
     */
    private void tearDown() throws IOException {
        logFileWriter.close();
        reportPrinter.stopAsync();
    }

    // Runnable to handle reading input lines from the socket
    public Runnable socketInputHandler(final Socket socket) throws IOException {
        return () -> {
            try {
                final InputStream inputStream = socket.getInputStream();
                final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder clientInput = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    processInput(line, socket, clientInput);
                }

                // Write the full client input to the file, O(1) instead of O(n)
                writeData(clientInput);
                // close connection
                socket.close();
            } catch (IOException e) {
                if (e.getClass() != SocketException.class) {
                    System.out.println("Lost connection to client");
                }
            }
        };
    }

    /**
     * Writes data to a file. First check if StringBuilder is not empty then remote the trailing line break and write to file,
     * otherwise skip.
     * @param clientInput input string builder to write to file
     * @throws IOException
     */
    private void writeData(StringBuilder clientInput) throws IOException {
        // remove trailing line break and write line to file only when string is not empty
        if (!clientInput.toString().equals("")) {
            clientInput.setLength(clientInput.length()-1);
            logFileWriter.writeLine(clientInput.toString());
        }
    }

    /**
     * Handle input from clients based on content
     *
     * @param line   line sent by client
     * @param socket socket for a specific client
     * @throws IOException
     */
    synchronized void processInput(String line, Socket socket, StringBuilder clientInput) throws IOException {
        if ("terminate".equals(line)) {
            terminate();
        } else {
            if (StringUtils.isNumeric(line) && line.length() == MAX_STRING_LENGTH) {
                if (cache.containsKey(line)) {
                    // If value already entered increment dupes count
                    reportPrinter.incrementDiffDupCount();
                } else {
                    // If new value increment the diff and total unique counts
                    reportPrinter.incrementDiffUniqueCount();
                    reportPrinter.incrementTotalUniqueCount();
                    // add line to string in progress
                    clientInput.append(line).append("\n");
                    // Add value to cache (1 is a random choice and is irrelevant to this service.)
                    cache.put(line, 1);
                }
            } else {
                // preserve previous valid inputs
                writeData(clientInput);
                socket.close();
            }
        }
    }

    void terminate() throws IOException {
        serverSocket.close();
        this.stopped = true;
        tearDown();
        clientExecutorService.shutdown();
        exit(0);
    }

    // Getters for unit tests
    ReportPrinter getReportPrinter() {
        return this.reportPrinter;
    }

    void setLogFileWriter(LogFileWriter logFileWriter) {
        this.logFileWriter = logFileWriter;
    }

    Hashtable<String, Integer> getCache() {
        return this.cache;
    }
}
