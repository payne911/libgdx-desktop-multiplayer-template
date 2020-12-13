package com.deadlyboundaries.server;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import lombok.extern.slf4j.Slf4j;

/**
 * Launches the server application.
 */
@Slf4j
public class ServerLauncher {

    private static final int PORT = 81;
    private static final long CONNECTION_TIMEOUT_IN_MINUTES = 5L;

    public static void main(String[] args) {
        log.info("Launching WebSocket server on port: " + PORT);
        final Vertx vertx = Vertx.vertx();
        final HttpServer server = vertx.createHttpServer();
        server.websocketHandler(webSocket -> {
            // Printing received packets to console:
            webSocket.frameHandler(frame -> log.info("Received packet: " + frame.textData()));
            // Sending a simple message:
            webSocket.writeFinalTextFrame("Hello from server!");
            // Closing the socket in 5 seconds:
            vertx.setTimer(getConnectionTimeoutInMs(), id -> webSocket.close());
        }).listen(PORT);
    }

    private static long getConnectionTimeoutInMs() {
        return CONNECTION_TIMEOUT_IN_MINUTES * 60 * 1000;
    }
}