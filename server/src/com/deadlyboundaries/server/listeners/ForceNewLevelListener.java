package com.deadlyboundaries.server.listeners;

import com.deadlyboundaries.server.model.ServerState;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.deadlyboundaries.common.model.entities.dynamic.allies.Player;
import com.deadlyboundaries.common.network.listeners.AbstractListener;
import com.deadlyboundaries.common.network.register.dto.ForceNewLevelDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ForceNewLevelListener extends AbstractListener<ForceNewLevelDto> {

    private final ServerState serverState;
    private final Server server;

    public ForceNewLevelListener(Server server, ServerState serverState) {
        super(ForceNewLevelDto.class);
        this.serverState = serverState;
        this.server = server;
    }

    @Override
    public void accept(Connection connection, ForceNewLevelDto forceNewLevelDto) {
        synchronized (serverState) {
            serverState.newLevel();
            serverState.getPlayers().stream()
                    .map(Player::getUuid)
                    .forEach(uuid -> server.sendToAllTCP(serverState.getGameInitDto(uuid)));
        }
    }
}
