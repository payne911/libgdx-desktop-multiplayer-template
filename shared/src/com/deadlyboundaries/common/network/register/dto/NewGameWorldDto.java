package com.deadlyboundaries.common.network.register.dto;

import com.deadlyboundaries.common.state.GameWorld;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class NewGameWorldDto implements Dto {
    public GameWorld newGameWorld;
}
