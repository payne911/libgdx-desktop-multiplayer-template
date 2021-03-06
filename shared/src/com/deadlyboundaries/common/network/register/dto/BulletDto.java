package com.deadlyboundaries.common.network.register.dto;

import com.deadlyboundaries.common.model.Identifiable;
import com.deadlyboundaries.common.utils.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class BulletDto implements Dto, Identifiable {

    @EqualsAndHashCode.Include
    public UUID uuid;

    float posX, posY, angle, speed, size;
}
