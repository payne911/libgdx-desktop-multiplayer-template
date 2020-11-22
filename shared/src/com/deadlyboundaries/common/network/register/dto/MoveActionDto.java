package com.deadlyboundaries.common.network.register.dto;

import com.deadlyboundaries.common.network.register.PlayerBroadcastable;
import com.deadlyboundaries.common.network.register.Timestamped;
import com.deadlyboundaries.common.utils.UUID;
import lombok.Data;

@Data
public final class MoveActionDto implements PlayerBroadcastable, Timestamped, Dto {

    public UUID sourcePlayerUuid;
    public long timestamp;
    public float destX, destY;
}
