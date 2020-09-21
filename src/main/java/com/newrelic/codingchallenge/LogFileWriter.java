package com.newrelic.codingchallenge;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility to manipulate file
 */
public class LogFileWriter {
    private static BufferedWriter bufferedWriter;
    // Default file name
    private String fileName = "numbers.log";


    /**
     * Parameterized constructor
     *
     * @param fileName
     */
    public LogFileWriter(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            this.fileName = fileName;
        }
    }

    /**
     * Reset file
     *
     * @throws IOException
     */
    public void setupFile() throws IOException {
        // Clear file if exists for a clean start
        deleteFile();
        bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
    }

    /**
     * Delete file
     *
     * @throws IOException
     */
    public void deleteFile() throws IOException {
        Files.deleteIfExists(Paths.get(fileName));
    }

    /**
     * Append line to the end of the file
     *
     * @param line new line to be appended
     * @throws IOException
     */
    public synchronized void writeLine(String line) throws IOException {
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    /**
     * Close buffered writer to the file
     *
     * @throws IOException
     */
    public void close() throws IOException {
        bufferedWriter.close();
    }
}
