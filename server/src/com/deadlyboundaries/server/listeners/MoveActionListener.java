package com.deadlyboundaries.server.listeners;

import com.deadlyboundaries.server.model.ServerState;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.deadlyboundaries.common.network.listeners.AbstractListener;
import com.deadlyboundaries.common.network.register.dto.MoveActionDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoveActionListener extends AbstractListener<MoveActionDto> {

    private final ServerState serverState;
    private final Server server;

    public MoveActionListener(Server server, ServerState serverState) {
        super(MoveActionDto.class);
        this.serverState = serverState;
        this.server = server;
    }

    @Override
    public void accept(Connection connection, MoveActionDto moveActionDto) {
        log.debug("Received MoveAction: " + moveActionDto);
        serverState.updatePlayerPos(moveActionDto);
        server.sendToAllTCP(moveActionDto);
    }
}
