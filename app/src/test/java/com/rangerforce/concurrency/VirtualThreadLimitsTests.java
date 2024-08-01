/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.rangerforce.concurrency.tasks.FibonacciSequenceTask;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

public class VirtualThreadLimitsTests {

    @Test
    public void when_maxThreads_then_executes_all() {
        // Arrange
        var maxThreads = 65_536;
        var threads = IntStream.range(0, maxThreads)
                .mapToObj(i ->
                        Thread.ofVirtual().name("platform-thread-" + i).unstarted(() -> new FibonacciSequenceTask(i)))
                .toList();

        // Act
        threads.forEach(Thread::start);

        // Assert
        threads.forEach(thread -> {
            assertDoesNotThrow(() -> thread.join());
        });
    }
}
