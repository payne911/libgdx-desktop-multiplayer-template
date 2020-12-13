//package com.deadlyboundaries.server;
//
//
//import com.github.czyzby.websocket.WebSocket;
//import com.github.czyzby.websocket.WebSocketAdapter;
//import com.github.czyzby.websocket.WebSocketListener;
//import com.github.czyzby.websocket.WebSockets;
//import com.github.czyzby.websocket.data.WebSocketCloseCode;
//import com.github.czyzby.websocket.net.ExtendedNet;
//
//import com.badlogic.gdx.Game;
//import com.deadlyboundaries.common.network.constants.NetworkConstants;
//import com.esotericsoftware.kryonet.Server;
//import java.util.Objects;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//
//public class DeadlyBoundariesServerGame extends Game {
//
//    private final WebSocket webSocket = ExtendedNet.getNet().newWebSocket("echo.websocket.org", 80);
//
//    private final Server server;
//    private final DeadlyBoundariesServerScreen serverScreen;
//
//
//    public DeadlyBoundariesServerGame() {
//        this.server = new Server(
//                NetworkConstants.WRITE_BUFFER_SIZE, NetworkConstants.OBJECT_BUFFER_SIZE);
//        server.getKryo().setRegistrationRequired(false);
//        server.getKryo().setWarnUnregisteredClasses(true);
//        this.serverScreen = new DeadlyBoundariesServerScreen(server);
//    }
//
//    @Override
//    @SneakyThrows
//    public void create() {
//        log.info("SERVER STARTING!!!");
//        server.bind(NetworkConstants.PORT);
//        server.start();
//        log.info("SERVER STARTED!!!");
//        setScreen(serverScreen);
//    }
//
//    @Override
//    public void dispose() {
//        if (Objects.nonNull(getScreen())) {
//            getScreen().dispose();
//        }
//    }
//}