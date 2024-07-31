package com.rangerforce.concurrency;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.SAME_THREAD)
public class PlatformThreadTests {

    @Test
    public void when_ofPlatform_then_executes() {
        // Arrange
        var thread = Thread.ofPlatform().name("platform-thread");

        // Act
        thread.start(() -> System.out.println("Hello from platform thread"));

        // Assert
        assertTrue(true);
    }

    @Test
    public void given_unstartedThread_when_join_then_executes() throws InterruptedException {
        // Arrange
        var number = new AtomicReference<>(0);
        var thread = Thread.ofPlatform().name("platform-thread")
                .unstarted(() -> number.set(1));

        // Act
        thread.start();
        thread.join();

        // Assert
        assertEquals(1, (int) number.get());
    }

    @Test
    public void given_SetNumberTask_when_join_then_executes() throws InterruptedException {
        // Arrange
        var expected = 1;
        var task = new SetNumberTask(expected);
        var thread = Thread.ofPlatform().name("platform-thread")
                .unstarted(task);

        // Act
        thread.start();
        thread.join();

        // Assert
        assertEquals(expected, task.getNumber());
    }
}
