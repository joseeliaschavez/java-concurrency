/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency;

public class FibonacciSequenceTask implements Runnable {
    private final int iterations;
    private int result;

    public FibonacciSequenceTask(int iterations) {
        this.iterations = iterations;
        result = 0;
    }

    @Override
    public void run() {
        int x = 0;
        int y = 1;
        for (int i = 1; i < iterations; i++) {
            result = x + y;
            x = y;
            y = result;
        }
    }

    public int getResult() {
        return result;
    }
}
