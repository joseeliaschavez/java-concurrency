/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class PlatformThreadLimitsTests {
  @Disabled("Running this test as part of the suite will cause one or more other tests to fail")
  @Test
  public void when_maxThreads_then_throwsOutOfMemoryError() {
    // Arrange
    var maxThreads = 8192;

    // Act, Assert
    assertThrows(
        OutOfMemoryError.class,
        () -> {
          IntStream.range(0, maxThreads)
              .forEach(
                  i -> {
                    var thread = Thread.ofPlatform().name("platform-thread-" + i);
                    thread.start(
                        () -> {
                          try {
                            Thread.sleep(1_000L);
                          } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                          }
                        });
                  });
        });
  }
}
