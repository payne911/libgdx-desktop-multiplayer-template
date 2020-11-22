package com.deadlyboundaries.server;

import com.badlogic.gdx.ScreenAdapter;
import com.deadlyboundaries.server.listeners.DebugListener;
import com.deadlyboundaries.server.model.ServerState;
import com.esotericsoftware.kryonet.Server;
import com.deadlyboundaries.common.network.register.dto.GameStateDto;
import com.deadlyboundaries.server.listeners.EnemyCollisionListener;
import com.deadlyboundaries.server.listeners.ForceNewLevelListener;
import com.deadlyboundaries.server.listeners.MoveActionListener;
import com.deadlyboundaries.server.listeners.PlayerAttackListener;
import com.deadlyboundaries.server.listeners.PlayerConnectionListener;
import com.deadlyboundaries.server.listeners.PlayerDisconnectionListener;
import com.deadlyboundaries.server.listeners.WeapongFacingListener;

public class DeadlyBoundariesServerScreen extends ScreenAdapter {

    private static final float LOOP_SPEED = 0.100F;

    private final Server server;
    private final ServerState serverState;

    private float deltaAcc;


    public DeadlyBoundariesServerScreen(Server server) {
        this.server = server;
        this.serverState = new ServerState();
        this.deltaAcc = 0;
    }

    @Override
    public void show() {
        server.addListener(new DebugListener(server));
        server.addListener(new PlayerConnectionListener(server, serverState));
        server.addListener(new MoveActionListener(server, serverState));
        server.addListener(new PlayerDisconnectionListener(server, serverState));
        server.addListener(new EnemyCollisionListener(serverState));
        server.addListener(new PlayerAttackListener(server));
        server.addListener(new WeapongFacingListener(serverState));
        server.addListener(new ForceNewLevelListener(server, serverState));
    }

    @Override
    public void render(float delta) {

        serverState.runGameLogic(delta);
        if (deltaAcc >= LOOP_SPEED) {
            deltaAcc = 0f; // or subtract the amount of LOOP_SPEED... as we decide
            GameStateDto gameStateDto = serverState.getCurrentGameStateAsDto();
            server.sendToAllTCP(gameStateDto);
//            serverState.resetLists();
        } else {
            deltaAcc += delta;
        }
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
