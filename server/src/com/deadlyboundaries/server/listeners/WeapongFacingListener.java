package com.deadlyboundaries.server.listeners;

import com.deadlyboundaries.server.model.ServerState;
import com.esotericsoftware.kryonet.Connection;
import com.deadlyboundaries.common.network.listeners.AbstractListener;
import com.deadlyboundaries.common.network.register.dto.WeaponFacingDto;

public class WeapongFacingListener extends AbstractListener<WeaponFacingDto> {

    private final ServerState serverState;

    public WeapongFacingListener(ServerState serverState) {
        super(WeaponFacingDto.class);
        this.serverState = serverState;
    }

    @Override
    public void accept(Connection conncetion, WeaponFacingDto elem) {
        serverState.updatePlayerFacingAngle(elem.playerUuid, elem.angle);
    }
}
