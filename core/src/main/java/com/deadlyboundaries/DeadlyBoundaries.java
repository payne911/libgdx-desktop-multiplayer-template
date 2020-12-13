package com.deadlyboundaries;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketAdapter;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.WebSockets;
import com.github.czyzby.websocket.data.WebSocketCloseCode;
import com.github.czyzby.websocket.data.WebSocketException;
import com.github.czyzby.websocket.net.ExtendedNet;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisWindow;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class DeadlyBoundaries extends ApplicationAdapter {

    /**
     * Default screen size.
     */
    public static final int WIDTH = 640, HEIGHT = 480;

    private Stage stage;
    private WebSocket webSocket;

    // Widgets:
    private Button connectingButton;
    private Button sendingButton;
    private Button disconnectingButton;

    private VisTextField hostInput;
    private VisTextField portInput;
    private Button secureButton;

    private VisTextField input;
    private VisLabel status;

    @Override
    public void create() {
        // Loading VisUI skin assets:
        VisUI.load(VisUI.SkinScale.X2);

        stage = new Stage(new FitViewport(WIDTH, HEIGHT));
        Gdx.input.setInputProcessor(stage);

        VisWindow window = new VisWindow("Web sockets test");
        TableUtils.setSpacingDefaults(window);
        window.setFillParent(true);

        // Buttons row:
        connectingButton = new VisTextButton("Open");
        sendingButton = new VisTextButton("Send");
        sendingButton.setDisabled(true);
        disconnectingButton = new VisTextButton("Close");
        disconnectingButton.setDisabled(true);
        VisTable table = new VisTable(true);
        table.defaults().growX();
        table.add(connectingButton);
        table.add(sendingButton);
        table.add(disconnectingButton);
        addListeners();
        window.add(table).growX().row();

        // Address rows:
        hostInput = new VisTextField("echo.websocket.org");
        hostInput.setMessageText("Enter host.");
        table = new VisTable(true);
        table.add("Host:");
        table.add(hostInput).growX();
        window.add(table).growX().row();

        secureButton = new VisCheckBox("Secure");
        portInput = new VisTextField("80");
        portInput.setTextFieldFilter(new VisTextField.TextFieldFilter.DigitsOnlyFilter());
        portInput.setMessageText("Enter port.");
        table = new VisTable(true);
        table.add("Port:");
        table.add(portInput);
        table.add(secureButton);
        window.add(table).growX().row();

        // Input row:
        input = new VisTextField("", "small");
        input.setMessageText("Enter packet content.");
        input.setDisabled(true);
        window.add(input).growX().row();

        // Status label:
        status = new VisLabel("Connect to the server to send a message.", "small");
        status.setColor(VisUI.getSkin().getColor("vis-blue"));
        status.setWrap(true);
        window.add(status).width(WIDTH - 10f).expandY().align(Align.top);

        window.pack();
        window.centerWindow();
        stage.addActor(window.fadeIn());
    }

    private void addListeners() {
        connectingButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                connect();
            }
        });
        sendingButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sendMessage();
            }
        });
        disconnectingButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                disconnect();
                Gdx.app.log("", "");
            }
        });
    }

    public void connect() {
        if (hostInput.isEmpty() || portInput.isEmpty()) {
            status.setText(
                    "Fill host and port. echo.websocket.org hosts a free web socket echo server at 80.");
        } else if (webSocket == null || !webSocket.isOpen()) {
            String host = hostInput.getText();
            int port = Integer.parseInt(portInput.getText());
            ExtendedNet net = ExtendedNet.getNet();
            if (secureButton.isChecked()) {
                webSocket = net.newSecureWebSocket(host, port);
            } else {
                webSocket = net.newWebSocket(host, port);
            }
            webSocket.addListener(getWebSocketListener());
            status.setText("Connecting...");
            try {
                webSocket.connect();
            } catch (WebSocketException exception) {
                status.setText("Cannot connect: " + exception.getMessage());
                Gdx.app.error("WS", "Cannot connect.", exception);
            }
        }
    }

    private WebSocketListener getWebSocketListener() {
        return new WebSocketAdapter() {
            @Override
            public boolean onOpen(WebSocket webSocket) {
                status.setText("Connected.");
                connectingButton.setDisabled(true);
                sendingButton.setDisabled(false);
                disconnectingButton.setDisabled(false);
                input.setDisabled(false);
                Gdx.app.log("WS", "Connected!");
                return FULLY_HANDLED;
            }

            @Override
            public boolean onMessage(WebSocket webSocket, String packet) {
                status.setText("Received message: " + packet);
                Gdx.app.log("WS", "Received message: " + packet);
                return FULLY_HANDLED;
            }

            @Override
            public boolean onClose(WebSocket webSocket, WebSocketCloseCode code, String reason) {
                status.setText("Disconnected.");
                connectingButton.setDisabled(false);
                sendingButton.setDisabled(true);
                disconnectingButton.setDisabled(true);
                input.setDisabled(true);
                Gdx.app.log("WS", "Disconnected!");
                return FULLY_HANDLED;
            }
        };
    }

    public void sendMessage() {
        String message = input.getText();
        if (webSocket != null && webSocket.isOpen()) {
            status.setText("Sent message: " + message);
            Gdx.app.log("WS", "Sending message: " + message);
            webSocket.send(message);
            input.clearText();
        }
    }

    public void disconnect() {
        WebSockets.closeGracefully(webSocket); // Null-safe closing method that logs exceptions
        Gdx.app.log("WS", "Closed connection.");
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        VisUI.dispose();
        stage.dispose();
        disconnect();
    }
}