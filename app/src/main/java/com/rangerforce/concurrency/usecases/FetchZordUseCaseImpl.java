/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency.usecases;

import com.rangerforce.concurrency.clients.PowerRangerClient;
import com.rangerforce.concurrency.clients.PowerRangerClientMockImpl;
import com.rangerforce.concurrency.domain.models.PowerRanger;
import com.rangerforce.concurrency.domain.models.Zord;
import com.rangerforce.concurrency.util.Stopwatch;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class FetchZordUseCaseImpl implements FetchZordUseCase {
    private final PowerRangerClient powerRangerClient;

    @Override
    public Zord fetch(String zordId) {
        var zord = new Zord();

        CompletableFuture.supplyAsync(() -> powerRangerClient.fetchZord(zordId))
                .thenComposeAsync(zordResponse -> {
                    zord.setName(zordResponse.getName());
                    return CompletableFuture.supplyAsync(
                                    () -> powerRangerClient.fetchPowerRanger(zordResponse.getPilotId()))
                            .thenApply(pilot -> new PowerRanger(
                                    pilot.getRangerName(), pilot.getCharacterName(), pilot.getActorName()));
                })
                .thenAccept(zord::setPilot)
                .join();

        return zord;
    }

    public static void main(String[] args) {
        var stopWatch = new Stopwatch();
        var powerRangerClient = new PowerRangerClientMockImpl();
        var fetchZordUseCase = new FetchZordUseCaseImpl(powerRangerClient);

        log.info("Total CPU Cores: {}", Runtime.getRuntime().availableProcessors());

        stopWatch.start();
        var zord = fetchZordUseCase.fetch("zord-5");
        var duration = stopWatch.stop();

        log.info("Zord: {}", zord);
        log.info("Duration: {} ms", duration);
    }
}
