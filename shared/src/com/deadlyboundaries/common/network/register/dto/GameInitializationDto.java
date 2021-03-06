package com.deadlyboundaries.common.network.register.dto;

import com.deadlyboundaries.common.state.GameWorld;
import com.deadlyboundaries.common.utils.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public final class GameInitializationDto implements Dto {

    public GameWorld newGameWorld;
    public long levelSeed = 0;
    public UUID currentPlayerId;
}
