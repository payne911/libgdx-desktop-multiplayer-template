package com.deadlyboundaries.client.network;

import com.deadlyboundaries.client.DeadlyBoundaries;
import com.esotericsoftware.kryonet.Client;
import com.deadlyboundaries.client.network.listeners.DebugListener;
import com.deadlyboundaries.client.network.listeners.GameInitializerListener;
import com.deadlyboundaries.client.network.test.IncrementalAverage;
import com.deadlyboundaries.common.network.constants.NetworkConstants;
import com.deadlyboundaries.common.network.register.dto.Dto;
import java.io.IOException;
import java.net.InetAddress;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class MyClient {

    @Setter
    private IncrementalAverage latencyReport;
    private final InetAddress addr;
    private final Client client;
    private final DeadlyBoundaries deadlyBoundaries;


    @SneakyThrows
    public MyClient(boolean isLocalServer, DeadlyBoundaries deadlyBoundaries) {
        this.client = new Client(
                NetworkConstants.WRITE_BUFFER_SIZE, NetworkConstants.OBJECT_BUFFER_SIZE);
        client.getKryo().setRegistrationRequired(false);
        client.getKryo().setWarnUnregisteredClasses(true);
        this.deadlyBoundaries = deadlyBoundaries;
        this.addr = isLocalServer
                ? InetAddress.getLocalHost()
                : InetAddress.getByName(NetworkConstants.REMOTE_SERVER_IP);
        this.latencyReport = new IncrementalAverage();
    }

    @SneakyThrows
    public void connect() {
//        client.getKryo().register(GameWorld.class, new JsonSerialization());
        client.addListener(new DebugListener());
        client.addListener(new GameInitializerListener(deadlyBoundaries, client));

        client.start();
        tryConnection(500, true);
    }

    private void tryConnection(long ms, boolean retry) throws InterruptedException {
        if (!retry) return;
        if (ms > 500) {
            Thread.sleep(ms);
        }
        try {
            client.connect(NetworkConstants.TIMEOUT, addr, NetworkConstants.PORT);
        } catch (IOException ex) {
            log.warn("Something went wrong while trying to connect to the server.");
            tryConnection(ms*2, ms > 3000);
            ex.printStackTrace();
        }
    }

    public <T extends Dto> void sendTCP(T dto) {
        client.sendTCP(dto);
    }
}
