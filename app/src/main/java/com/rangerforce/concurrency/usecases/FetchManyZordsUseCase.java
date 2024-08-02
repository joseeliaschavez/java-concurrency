/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency.usecases;

import com.rangerforce.concurrency.clients.PowerRangerClient;
import com.rangerforce.concurrency.clients.PowerRangerClientMockImpl;
import com.rangerforce.concurrency.domain.models.Zord;
import com.rangerforce.concurrency.util.Stopwatch;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class FetchManyZordsUseCase {
    private final PowerRangerClient powerRangerClient;

    public List<Zord> fetch(String... zordIds) {
        List<Zord> zords;

        /**
         * We are using the Java 21 StructuredTaskScope preview feature to manage the lifecycle of the tasks.
         */
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var zordTasks = Arrays.stream(zordIds)
                    .map(zordId -> scope.fork(() -> powerRangerClient.fetchZord(zordId)))
                    .toList();

            scope.join().throwIfFailed();

            zords = zordTasks.stream()
                    .map(zordTask -> {
                        var response = zordTask.get();
                        return new Zord(response.getName(), null);
                    })
                    .toList();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return zords;
    }

    public static void main(String[] args) {
        var stopWatch = new Stopwatch();
        var powerRangerClient = new PowerRangerClientMockImpl();
        var fetchMegazordUseCase = new FetchManyZordsUseCase(powerRangerClient);

        log.info("Total CPU Cores: {}", Runtime.getRuntime().availableProcessors());

        stopWatch.start();
        var zords = fetchMegazordUseCase.fetch("zord-0", "zord-5");
        var duration = stopWatch.stop();

        log.info("Zords: {}", zords);
        log.info("Duration: {} ms", duration);
    }
}
