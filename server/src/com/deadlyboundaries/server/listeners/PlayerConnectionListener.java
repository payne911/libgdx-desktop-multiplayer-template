package com.deadlyboundaries.server.listeners;

import com.deadlyboundaries.server.model.ServerState;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.deadlyboundaries.common.model.DeadlyBoundariesException;
import com.deadlyboundaries.common.model.entities.dynamic.allies.Player;
import com.deadlyboundaries.common.network.listeners.AbstractListener;
import com.deadlyboundaries.common.network.register.dto.PlayerConnectionDto;
import com.deadlyboundaries.common.utils.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayerConnectionListener extends AbstractListener<PlayerConnectionDto> {

    private final Server server;
    private final ServerState serverState;

    public PlayerConnectionListener(Server server, ServerState serverState) {
        super(PlayerConnectionDto.class);
        this.server = server;
        this.serverState = serverState;
    }

    @Override
    public void accept(Connection connection, PlayerConnectionDto playerConnection) {
        if (serverState.isEmptyRoom()) {
            serverState.initializeOnFirstPlayerConnected();
        }
        UUID uuid = UUID.getNext();
        Player<?> player = null;
        try {
            player = playerConnection.playerType
                    .getPlayerInstance(uuid, serverState.getFreeColor(uuid),
                            serverState.getServerWorldManager().getMutableGameWorld().randomPos());
            serverState.addPlayer(player);
            server.sendToTCP(connection.getID(), serverState.getGameInitDto(uuid));
            server.sendToAllExceptTCP(connection.getID(), player);
        } catch (DeadlyBoundariesException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            // todo: send Connection Refused
        }
    }
}
