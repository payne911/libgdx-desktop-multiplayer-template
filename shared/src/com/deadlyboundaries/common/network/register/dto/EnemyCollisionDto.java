package com.deadlyboundaries.common.network.register.dto;

import com.deadlyboundaries.common.utils.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class EnemyCollisionDto implements Dto {

    public UUID playerUuid;
    public UUID enemyUuid;
}
