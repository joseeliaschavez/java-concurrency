/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency.clients;

import com.rangerforce.concurrency.clients.datum.MegazordResponse;
import com.rangerforce.concurrency.clients.datum.PowerRangerResponse;
import com.rangerforce.concurrency.clients.datum.ZordResponse;

public interface PowerRangerClient {
    MegazordResponse fetchMegazord(String megazordId);

    ZordResponse fetchZord(String zordId);

    PowerRangerResponse fetchPowerRanger(String rangerId);
}
