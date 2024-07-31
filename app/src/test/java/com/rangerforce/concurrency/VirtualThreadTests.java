/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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

    @Test
    public void given_singleFibonacciSequenceTask_when_join_then_executes() {
        // Arrange
        var expected = 18;
        var task = new FibonacciSequenceTask(expected);
        var thread = Thread.ofVirtual().name("virtual-thread").unstarted(task);

        // Act
        thread.start();

        // Assert
        assertDoesNotThrow(() -> thread.join());
        assertEquals(2584, task.getResult());
    }

    @ParameterizedTest
    @MethodSource("fibonacciNumbers")
    public void given_manyFibonacciSequenceTasks_when_join_then_executes(int n, int fibonacciNumber) {
        // Arrange
        var task = new FibonacciSequenceTask(n);
        var thread = Thread.ofVirtual().name("virtual-thread").unstarted(task);

        // Act
        thread.start();

        // Assert
        assertDoesNotThrow(() -> thread.join());
        assertEquals(fibonacciNumber, task.getResult());
    }

    private static Stream<Arguments> fibonacciNumbers() {

        return Stream.of(
                Arguments.of(2, 1),
                Arguments.of(3, 2),
                Arguments.of(4, 3),
                Arguments.of(5, 5),
                Arguments.of(6, 8),
                Arguments.of(7, 13),
                Arguments.of(8, 21),
                Arguments.of(9, 34),
                Arguments.of(10, 55),
                Arguments.of(11, 89),
                Arguments.of(12, 144),
                Arguments.of(13, 233),
                Arguments.of(14, 377),
                Arguments.of(15, 610),
                Arguments.of(16, 987),
                Arguments.of(17, 1597),
                Arguments.of(18, 2584),
                Arguments.of(19, 4181),
                Arguments.of(20, 6765),
                Arguments.of(21, 10946));
    }
}
