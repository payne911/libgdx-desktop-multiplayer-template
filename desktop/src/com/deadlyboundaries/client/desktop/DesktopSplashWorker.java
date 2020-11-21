package com.deadlyboundaries.client.desktop;

import com.deadlyboundaries.client.splashScreen.ISplashWorker;

import java.awt.*;


/**
 * Manages the SplashScreen displayed while waiting for the application to load the JVM library.
 */
public class DesktopSplashWorker implements ISplashWorker {

    @Override
    public void closeSplashScreen() {
        SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash != null) {
            splash.close();
        }
    }
}
