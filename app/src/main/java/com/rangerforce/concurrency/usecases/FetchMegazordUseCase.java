/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency.usecases;

import com.rangerforce.concurrency.clients.PowerRangerClient;
import com.rangerforce.concurrency.clients.PowerRangerClientMockImpl;
import com.rangerforce.concurrency.domain.models.Megazord;
import com.rangerforce.concurrency.domain.models.PowerRanger;
import com.rangerforce.concurrency.domain.models.Zord;
import java.util.concurrent.CompletableFuture;

import com.rangerforce.concurrency.util.Stopwatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class FetchMegazordUseCase {
    private final PowerRangerClient powerRangerClient;

    public Megazord fetch(String megazordId) {
        var megazord = new Megazord();

        var future = CompletableFuture.supplyAsync(() -> powerRangerClient.fetchMegazord(megazordId))
                .thenApply(megazordResponse -> {
                    megazord.setName(megazordResponse.getName());
                    return megazordResponse.getZordIds();
                })
                .thenCompose(zordIds -> CompletableFuture.allOf(zordIds.stream()
                                .map(zordId -> CompletableFuture.supplyAsync(() -> fetchZord(zordId))
                                        .thenAccept(zord -> megazord.getZords().add(zord.join())))
                                .toArray(CompletableFuture[]::new))
                        .thenApply(v -> megazord))
                .join();

        return megazord;
    }

    private CompletableFuture<Zord> fetchZord(String zordId) {
        return CompletableFuture.supplyAsync(() -> powerRangerClient.fetchZord(zordId))
                .thenComposeAsync(
                        zordResponse -> CompletableFuture.supplyAsync(() -> fetchPowerRanger(zordResponse.getPilotId()))
                                .thenApply(pilot -> new Zord(zordResponse.getName(), pilot.join())));
    }

    private CompletableFuture<PowerRanger> fetchPowerRanger(String powerRangerId) {
        return CompletableFuture.supplyAsync(() -> powerRangerClient.fetchPowerRanger(powerRangerId))
                .thenApply(response -> new PowerRanger(
                        response.getRangerName(), response.getCharacterName(), response.getActorName()));
    }

    public static void main(String[] args) {
        var stopWatch = new Stopwatch();
        var powerRangerClient = new PowerRangerClientMockImpl();
        var fetchMegazordUseCase = new FetchMegazordUseCase(powerRangerClient);

        stopWatch.start();
        var megazord = fetchMegazordUseCase.fetch("megazord-0");
        var duration = stopWatch.stop();

        log.info("Megazord: {}", megazord);
        log.info("Duration: {} ms", duration);
    }
}
