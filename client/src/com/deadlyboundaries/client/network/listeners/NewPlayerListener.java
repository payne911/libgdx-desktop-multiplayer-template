package com.deadlyboundaries.client.network.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.deadlyboundaries.common.model.entities.dynamic.allies.Player;
import com.deadlyboundaries.common.network.listeners.AbstractListener;
import com.deadlyboundaries.common.state.LocalGameState;
import com.deadlyboundaries.common.utils.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NewPlayerListener extends AbstractListener<Player> {

    private final LocalGameState localGameState;
    private final UUID selfPlayerUuid;

    public NewPlayerListener(LocalGameState localGameState, UUID selfPlayerUuid) {
        super(Player.class);
        this.localGameState = localGameState;
        this.selfPlayerUuid = selfPlayerUuid;
    }

    @Override
    public void accept(Connection connection, Player newPlayer) {
        log.info("New player connected: " + newPlayer);
        if (newPlayer.getUuid().equals(selfPlayerUuid)) {
            // todo: shouldn't happen? consider removing this eventually
            log.warn("Ignoring message of self being a new player.");
            return;
        }

        localGameState.addPlayer(newPlayer);
    }
}
