package com.deadlyboundaries.server.model;

import com.deadlyboundaries.server.factories.EnemySpawner;
import com.deadlyboundaries.common.model.entities.dynamic.enemies.Enemy;
import com.deadlyboundaries.common.state.GameWorld;
import com.deadlyboundaries.common.state.GameWorldManager;
import java.util.ArrayList;
import lombok.Getter;

public class ServerWorldManager extends GameWorldManager {

    @Getter
    private final EnemySpawner enemySpawner;

    @Getter
    private final ArrayList<Enemy> spawnedEnemies = new ArrayList<>();

    public ServerWorldManager(GameWorld initialGameWorld) {
        super(initialGameWorld);
        this.enemySpawner = new EnemySpawner(initialGameWorld.getLocalGameState());
    }

    @Override
    public void updateGameState(float delta) {
        spawnEnemies(delta);
        commonGameStateUpdate(delta);
    }

    private void spawnEnemies(float delta) {
        var spawned = enemySpawner.update(delta, mutableGameWorld.getLevel().getAllSpawnPoints());
        spawnedEnemies.addAll(spawned);
    }

    public ArrayList<Enemy> extractNewEnemies() {
        ArrayList<Enemy> returnedList = new ArrayList<>(spawnedEnemies);
        spawnedEnemies.clear();
        return returnedList;
    }
}
