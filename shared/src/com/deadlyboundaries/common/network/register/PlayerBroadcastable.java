package com.deadlyboundaries.common.network.register;

import com.deadlyboundaries.common.utils.UUID;

public interface PlayerBroadcastable {

    UUID getSourcePlayerUuid();

    default boolean shouldBeIgnored(UUID currentPlayerUuid) {
        return getSourcePlayerUuid().equals(currentPlayerUuid);
    }
}
