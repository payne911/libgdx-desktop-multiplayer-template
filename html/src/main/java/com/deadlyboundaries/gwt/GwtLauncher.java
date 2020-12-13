package com.deadlyboundaries.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.deadlyboundaries.DeadlyBoundaries;
import com.github.czyzby.websocket.GwtWebSockets;

/**
 * Launches the GWT application.
 */
public class GwtLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        GwtApplicationConfiguration configuration = new GwtApplicationConfiguration(
                DeadlyBoundaries.WIDTH, DeadlyBoundaries.HEIGHT);
        return configuration;
    }

    @Override
    public ApplicationListener createApplicationListener() {
        // Initiating GWT web sockets module:
        GwtWebSockets.initiate();
        return new DeadlyBoundaries();
    }
}