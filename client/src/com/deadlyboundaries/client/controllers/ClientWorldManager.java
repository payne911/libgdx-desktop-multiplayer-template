package com.deadlyboundaries.client.controllers;

import com.deadlyboundaries.common.state.GameWorld;
import com.deadlyboundaries.common.state.GameWorldManager;

public class ClientWorldManager extends GameWorldManager {


    public ClientWorldManager(GameWorld initialGameWorld) {
        super(initialGameWorld);
    }

    @Override
    public void updateGameState(float delta) {
        commonGameStateUpdate(delta);
        animateBases(delta);
    }

    private void animateBases(float delta) {
        mutableGameWorld.getLevel().getAllPlayerBases().forEach(b -> b.update(delta));
    }
}
