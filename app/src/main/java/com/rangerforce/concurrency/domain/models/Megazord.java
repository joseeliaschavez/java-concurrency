/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency.domain.models;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Megazord {
    String name;
    List<Zord> zords;
    List<PowerRanger> pilots;

    public Megazord() {
        this.name = "";
        this.zords = new ArrayList<>();
        this.pilots = new ArrayList<>();
    }
}
