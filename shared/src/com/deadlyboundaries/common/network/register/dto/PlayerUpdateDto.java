package com.deadlyboundaries.common.network.register.dto;

import com.deadlyboundaries.common.model.Identifiable;
import com.deadlyboundaries.common.utils.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerUpdateDto implements Dto, Identifiable {

    public UUID uuid;
    public float health, angle;
    public boolean healthHasChanged;
}
