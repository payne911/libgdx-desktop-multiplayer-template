package com.deadlyboundaries.common.network.constants;

public final class NetworkConstants {

    private NetworkConstants() {
    }

    public static final int WRITE_BUFFER_SIZE = 65536;
    public static final int OBJECT_BUFFER_SIZE = 8192 * 4;

    public static final int PORT = 81; // assumes published port by Docker image is same as Host
    public static final int TIMEOUT = 15000;
    public static final String REMOTE_SERVER_IP = "52.60.181.140";
}
