package com.deadlyboundaries.common.network.register.dto;

import com.deadlyboundaries.common.model.entities.dynamic.enemies.Enemy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class NewEnemyDto implements Dto {

    public Enemy enemy;
}
