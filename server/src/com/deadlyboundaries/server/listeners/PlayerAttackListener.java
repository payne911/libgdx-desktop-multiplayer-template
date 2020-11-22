package com.deadlyboundaries.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.deadlyboundaries.common.network.listeners.AbstractListener;
import com.deadlyboundaries.common.network.register.dto.RangedPlayerAttackDto;

public class PlayerAttackListener extends AbstractListener<RangedPlayerAttackDto> {

    private Server server;

    public PlayerAttackListener(Server server) {
        super(RangedPlayerAttackDto.class);
        this.server = server;
    }

    @Override
    public void accept(Connection connection, RangedPlayerAttackDto elem) {
        server.sendToAllExceptTCP(connection.getID(), elem);
    }
}
