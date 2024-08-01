/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency.usecases;

import com.rangerforce.concurrency.clients.PowerRangerClient;
import com.rangerforce.concurrency.clients.PowerRangerClientMockImpl;
import com.rangerforce.concurrency.domain.models.Megazord;
import com.rangerforce.concurrency.domain.models.PowerRanger;
import com.rangerforce.concurrency.domain.models.Zord;
import com.rangerforce.concurrency.util.Stopwatch;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class FetchMegazordUseCase {
    private final PowerRangerClient powerRangerClient;

    public Megazord fetch(String megazordId) {
        var megazord = new Megazord();

        CompletableFuture.supplyAsync(() -> powerRangerClient.fetchMegazord(megazordId))
                .thenApply(megazordResponse -> {
                    megazord.setName(megazordResponse.getName());
                    return megazordResponse.getZordIds();
                })
                .thenComposeAsync(this::fetchAllZords)
                .thenApply(zords -> {
                    megazord.setZords(zords);
                    return zords.stream().map(Zord::getPilot).toList();
                })
                .thenAccept(megazord::setPilots)
                .join();

        return megazord;
    }

    private CompletableFuture<List<Zord>> fetchAllZords(List<String> zordIds) {
        var zordFutures = zordIds.stream().map(this::fetchZord).toList();

        return CompletableFuture.allOf(zordFutures.toArray(new CompletableFuture[0]))
                .thenApply(
                        v -> zordFutures.stream().map(CompletableFuture::join).toList());
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

        log.info("Total CPU Cores: {}", Runtime.getRuntime().availableProcessors());

        stopWatch.start();
        var megazord = fetchMegazordUseCase.fetch("megazord-0");
        var duration = stopWatch.stop();

        log.info("Megazord: {}", megazord);
        log.info("Duration: {} ms", duration);
    }
}
