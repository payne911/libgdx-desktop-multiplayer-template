package com.deadlyboundaries.common.model;

import com.deadlyboundaries.common.utils.UUID;

public interface Identifiable {
    UUID getUuid();

    default boolean isEquals(UUID other) {
        return getUuid().equals(other);
    }
}
