package com.deadlyboundaries.server;

import com.badlogic.gdx.backends.headless.HeadlessApplication;

public class DeadlyBoundariesServerLauncher {
    public static void main(String[] args) {

        new HeadlessApplication(new DeadlyBoundariesServerGame());
    }
}
