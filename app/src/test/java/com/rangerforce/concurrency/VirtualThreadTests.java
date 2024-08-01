/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency;

import static org.junit.jupiter.api.Assertions.*;

import com.rangerforce.concurrency.tasks.FibonacciSequenceTask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class VirtualThreadTests {

    @Test
    public void when_ofVirtual_then_executes() {
        // Arrange
        var thread = Thread.ofVirtual().name("virtual-thread");

        // Act
        thread.start(() -> System.out.println("Hello from virtual thread"));

        // Assert
        assertTrue(true);
    }

    @ParameterizedTest
    @MethodSource("com.rangerforce.concurrency.FibonacciNumbers#fibonacciNumbers")
    public void given_FibonacciSequenceTasks_when_join_then_executes(int n, int fibonacciNumber) {
        // Arrange
        var task = new FibonacciSequenceTask(n);
        var thread = Thread.ofVirtual().name("virtual-thread").unstarted(task);

        // Act
        thread.start();

        // Assert
        assertDoesNotThrow(() -> thread.join());
        assertEquals(fibonacciNumber, task.getResult());
    }
}
