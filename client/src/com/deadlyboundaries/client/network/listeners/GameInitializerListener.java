package com.deadlyboundaries.client.network.listeners;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.deadlyboundaries.client.DeadlyBoundaries;
import com.deadlyboundaries.client.MyGame;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.deadlyboundaries.client.controllers.Controller;
import com.deadlyboundaries.client.inputProcessors.MyGestureListener;
import com.deadlyboundaries.client.inputProcessors.MyInputProcessor;
import com.deadlyboundaries.client.screens.GameScreen;
import com.deadlyboundaries.common.model.entities.dynamic.allies.Player;
import com.deadlyboundaries.common.network.listeners.AbstractListener;
import com.deadlyboundaries.common.network.register.dto.GameInitializationDto;
import com.deadlyboundaries.common.state.GameWorld;
import com.deadlyboundaries.common.state.GameWorldManager;
import com.deadlyboundaries.common.state.LocalGameState;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;


/**
 * Used to initialize the game. Lots of variables being instantiated here.
 * <p>
 * The {@link GameScreen} is never shown before this class completes the initialization.
 */
@Slf4j
public class GameInitializerListener extends AbstractListener<GameInitializationDto> {

    /**
     * Used to call {@link Game#setScreen(Screen)}.
     */
    private final DeadlyBoundaries deadlyBoundaries;

    /**
     * Used by the {@link GameWorldManager} to do deep copies.
     */
    private final Client kClient;

    public GameInitializerListener(DeadlyBoundaries deadlyBoundaries, Client kryoClient) {
        super(GameInitializationDto.class);
        this.deadlyBoundaries = deadlyBoundaries;
        this.kClient = kryoClient;
    }

    @Override
    public void accept(Connection connection, GameInitializationDto gameInit) {
        if (Objects.isNull(gameInit)) {
            throw new IllegalStateException("Server did not send a valid GameState.");
        }
        log.debug("Received initial GS: " + gameInit);
        Optional<Player> selfPlayer = gameInit.newGameWorld
                .getLocalGameState().getPlayer(gameInit.getCurrentPlayerId());

        if (selfPlayer.isEmpty() || Objects.isNull(selfPlayer.get().getUuid())) {
            throw new IllegalStateException(
                    "Server did not send a valid GameState (it does not contain the new Player or he is labeled with the wrong ID).");
        }

        GameWorld gameWorld = gameInit.newGameWorld;

        // calculate the enemy map
        gameWorld.getLevel().getAllSpawnPoints()
                .forEach(s -> s.findPathToBase(gameWorld.getLevel()));

        log.info("before controller");
        MyGame.controller = new Controller(kClient, gameWorld, gameInit.currentPlayerId);
        log.info("controller: " + MyGame.controller);

        /* Input processors. */
        MyGestureListener inputProcessor1 = new MyGestureListener(
                MyGame.stage.getViewport(), MyGame.controller);
        MyInputProcessor inputProcessor2 = new MyInputProcessor(
                MyGame.stage.getViewport(), MyGame.controller);
        Gdx.input.setInputProcessor(
                new InputMultiplexer(MyGame.stage, new GestureDetector(inputProcessor1), inputProcessor2));

        /* Draw the screen to start the game. */
        Gdx.app.postRunnable(() -> {
            MyGame.LEVEL_SEED = gameWorld.getLevel().getSeed();
            deadlyBoundaries.setScreen(new GameScreen(MyGame.controller));

            LocalGameState localGameState = MyGame.controller.getGameWorld().getLocalGameState();

            /* Listeners which require the GameScreen to have been initialized. */
            kClient.addListener(new GameStateListener());
//            kClient.addListener(new LagListener(0, 0, new GameStateListener()));
            kClient.addListener(new MoveActionListener(localGameState, gameInit.currentPlayerId));
            kClient.addListener(new NewGameWorldListener(MyGame.controller.getClientWorldManager()));
            kClient.addListener(new NewPlayerListener(localGameState, gameInit.currentPlayerId));
            kClient.addListener(new PlayerDisconnectListener(localGameState));
            kClient.addListener(new RangedPlayerAttackListener(localGameState));
        });
    }
}
