package com.deadlyboundaries.client.network.listeners;

import com.deadlyboundaries.client.MyGame;
import com.esotericsoftware.kryonet.Connection;
import com.deadlyboundaries.common.network.listeners.AbstractListener;
import com.deadlyboundaries.common.network.register.dto.GameStateDto;


public class GameStateListener extends AbstractListener<GameStateDto> {

    public GameStateListener() {
        super(GameStateDto.class);
    }

    @Override
    public void accept(Connection connection, GameStateDto gameStateDto) {
        if (MyGame.controller != null) {
            MyGame.controller.getClientWorldManager().reconcile(gameStateDto);
        }
    }
}
