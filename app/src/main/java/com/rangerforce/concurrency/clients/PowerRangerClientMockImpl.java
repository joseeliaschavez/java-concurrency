/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency.clients;

import com.rangerforce.concurrency.clients.datum.MegazordResponse;
import com.rangerforce.concurrency.clients.datum.PowerRangerResponse;
import com.rangerforce.concurrency.clients.datum.ZordResponse;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PowerRangerClientMockImpl implements PowerRangerClient {

    @Override
    public MegazordResponse fetchMegazord(String megazordId) {
        log.debug("Fetching megazord: {}", megazordId);

        try {
            Thread.sleep(Duration.ofMillis(getDelay()));
        } catch (InterruptedException e) {
            log.error("Error fetching megazord", e);
        }

        if (!megazordId.endsWith("-0")) {
            throw new IllegalArgumentException("Invalid megazord id");
        }

        // Return Dino Megazord with 5 zords
        var zordIds = IntStream.range(0, 5).mapToObj(i -> "zord-" + i).toList();

        return new MegazordResponse(megazordId, "Dino Megazord", zordIds);
    }

    @Override
    public ZordResponse fetchZord(String zordId) {
        log.debug("Fetching zord: {}", zordId);

        try {
            Thread.sleep(Duration.ofMillis(getDelay()));
        } catch (InterruptedException e) {
            log.error("Error fetching zord", e);
        }

        return switch (zordId) {
            case "zord-0" -> new ZordResponse(zordId, "Tyrannosaurus", "ranger-0");
            case "zord-1" -> new ZordResponse(zordId, "Mastodon", "ranger-1");
            case "zord-2" -> new ZordResponse(zordId, "Triceratops", "ranger-2");
            case "zord-3" -> new ZordResponse(zordId, "Saber-Toothed Tiger", "ranger-3");
            case "zord-4" -> new ZordResponse(zordId, "Pterodactyl", "ranger-4");
            case "zord-5" -> new ZordResponse(zordId, "Dragonzord", "ranger-5");
            default -> throw new IllegalArgumentException("Invalid zord id");
        };
    }

    @Override
    public PowerRangerResponse fetchPowerRanger(String rangerId) {
        log.debug("Fetching power ranger: {}", rangerId);

        try {
            Thread.sleep(Duration.ofMillis(getDelay()));
        } catch (InterruptedException e) {
            log.error("Error fetching power ranger", e);
        }

        return switch (rangerId) {
            case "ranger-0" -> new PowerRangerResponse(rangerId, "Red Ranger", "Jason Lee Scott", "Austin St. John");
            case "ranger-1" -> new PowerRangerResponse(rangerId, "Black Ranger", "Zack Taylor", "Walter Emanuel Jones");
            case "ranger-2" -> new PowerRangerResponse(rangerId, "Blue Ranger", "Billy Cranston", "David Yost");
            case "ranger-3" -> new PowerRangerResponse(rangerId, "Yellow Ranger", "Trini Kwan", "Thuy Trang");
            case "ranger-4" -> new PowerRangerResponse(rangerId, "Pink Ranger", "Kimberly Ann Hart", "Amy Jo Johnson");
            case "ranger-5" -> new PowerRangerResponse(rangerId, "Green Ranger", "Tommy Oliver", "Jason David Frank");
            default -> throw new IllegalArgumentException("Invalid ranger id");
        };
    }

    private long getDelay() {
        return ThreadLocalRandom.current().nextLong(100);
    }
}
