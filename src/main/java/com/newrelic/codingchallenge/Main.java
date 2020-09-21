package com.newrelic.codingchallenge;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        new NumberServer(4000, "numbers.log").start();
    }
}
