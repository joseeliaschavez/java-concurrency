/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency.util;

public class Stopwatch {
    private long startTime;
    private long endTime;

    public void start() {
        startTime = System.currentTimeMillis();
        endTime = 0;
    }

    public long stop() {
        endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
