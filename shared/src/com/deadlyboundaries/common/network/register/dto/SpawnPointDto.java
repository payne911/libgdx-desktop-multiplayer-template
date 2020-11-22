package com.deadlyboundaries.common.network.register.dto;

import com.deadlyboundaries.common.model.Identifiable;
import com.deadlyboundaries.common.utils.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SpawnPointDto implements Dto, Identifiable {
    public float hp;
    public UUID uuid;
}
