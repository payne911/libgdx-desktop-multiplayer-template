package com.deadlyboundaries.server.listeners;

import com.deadlyboundaries.server.model.ServerState;
import com.esotericsoftware.kryonet.Connection;
import com.deadlyboundaries.common.network.listeners.AbstractListener;
import com.deadlyboundaries.common.network.register.dto.EnemyCollisionDto;

public class EnemyCollisionListener extends AbstractListener<EnemyCollisionDto> {

    private final ServerState serverState;

    public EnemyCollisionListener(ServerState serverState) {
        super(EnemyCollisionDto.class);
        this.serverState = serverState;
    }

    @Override
    public void accept(Connection conncetion, EnemyCollisionDto elem) {
        serverState.getEnemyCollisions().add(elem);
    }
}
