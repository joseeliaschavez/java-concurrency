/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency.usecases;

import com.rangerforce.concurrency.domain.models.Megazord;

public interface FetchMegazordUseCase {
    Megazord fetch(String megazordId);
}
