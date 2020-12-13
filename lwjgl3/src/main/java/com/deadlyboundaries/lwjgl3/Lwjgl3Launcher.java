package com.deadlyboundaries.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.deadlyboundaries.DeadlyBoundaries;
import com.github.czyzby.websocket.CommonWebSockets;

/**
 * Launches the desktop (LWJGL3) application.
 */
public class Lwjgl3Launcher {

    public static void main(final String[] args) {
        // Initiating web sockets module:
        CommonWebSockets.initiate();
        createApplication();
    }

    private static void createApplication() {
        new Lwjgl3Application(new DeadlyBoundaries(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        final Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("deadly-boundaries");
        configuration.setWindowedMode(DeadlyBoundaries.WIDTH, DeadlyBoundaries.HEIGHT);
        return configuration;
    }
}