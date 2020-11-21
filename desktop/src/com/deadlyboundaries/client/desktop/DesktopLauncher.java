package com.deadlyboundaries.client.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.deadlyboundaries.client.DeadlyBoundary;
import com.marvelousbob.common.network.constants.GameConstant;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = GameConstant.SIZE_X;
        config.height = GameConstant.SIZE_Y;
//        config.fullscreen = true;

        DeadlyBoundary core = new DeadlyBoundary();
        core.setSplashWorker(new DesktopSplashWorker());
        new LwjglApplication(core, config);
    }
}
