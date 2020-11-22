package com.deadlyboundaries.common.network.register.dto;

import com.deadlyboundaries.common.model.Identifiable;
import com.deadlyboundaries.common.utils.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayersBaseDto implements Dto, Identifiable {

    public UUID uuid;
    public float x, y, width, height, hp, maxHp;
}
