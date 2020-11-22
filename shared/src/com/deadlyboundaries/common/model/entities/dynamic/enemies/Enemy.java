package com.deadlyboundaries.common.model.entities.dynamic.enemies;

import com.deadlyboundaries.common.utils.UUID;
import com.deadlyboundaries.common.utils.movements.MovementStrategy;
import com.deadlyboundaries.common.model.Identifiable;
import com.deadlyboundaries.common.model.entities.Drawable;
import com.deadlyboundaries.common.model.entities.Movable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class Enemy implements Drawable, Identifiable, Movable {

    protected float hp, maxHp = 100;

    @EqualsAndHashCode.Include
    protected UUID uuid;

    protected UUID spawnPointUuid;

    protected MovementStrategy movementStrategy;

    public Enemy(UUID uuid, UUID spawnPointUuid, MovementStrategy movementStrategy) {
        this.uuid = uuid;
        this.movementStrategy = movementStrategy;
        this.spawnPointUuid = spawnPointUuid;
        this.hp = maxHp;
    }

    public void dealDamage(float damage) {
        this.hp -= damage;
    }
}
