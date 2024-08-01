/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency.usecases;

import com.rangerforce.concurrency.domain.models.Zord;

public interface FetchZordUseCase {
    Zord fetch(String zordId);
}
