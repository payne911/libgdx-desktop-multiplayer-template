package com.deadlyboundaries.common.model.entities.dynamic.projectiles;

import com.badlogic.gdx.math.Vector2;
import com.deadlyboundaries.common.utils.UUID;
import com.deadlyboundaries.common.model.Identifiable;
import com.deadlyboundaries.common.model.entities.Drawable;
import com.deadlyboundaries.common.model.entities.Movable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Bullet implements Drawable, Identifiable, Movable {

   public Bullet(UUID uuid) {
      this.uuid = uuid;
   }

   protected Vector2 currentPos;
   protected float angle, speed, size;

   @EqualsAndHashCode.Include
   protected UUID uuid;

   public abstract void updatePos(float delta);
}
