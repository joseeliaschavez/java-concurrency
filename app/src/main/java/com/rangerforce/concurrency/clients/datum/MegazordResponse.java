/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency.clients.datum;

import java.util.List;
import lombok.Value;

@Value
public class MegazordResponse {
    String id;
    String name;
    List<String> zordIds;
}
