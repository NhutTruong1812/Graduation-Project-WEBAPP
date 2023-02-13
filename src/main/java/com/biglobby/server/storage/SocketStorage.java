package com.biglobby.server.storage;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketStorage {

    private static final Logger logger = LoggerFactory.getLogger(SocketStorage.class);

    private static SocketStorage instance;
    private Map<String, String> sockets;

    private SocketStorage() {
        sockets = new HashMap<>();
    }

    public static synchronized SocketStorage getInstance() {
        if (instance == null) {
            instance = new SocketStorage();
        }
        return instance;
    }

    public Map<String, String> getSockets() {
        return sockets;
    }

    public void addSocket(String socketId, String username) {
        if (!sockets.containsKey(socketId)) {
            logger.debug("Socket: " + socketId + " already exist!");
            return;
        }
        sockets.put(socketId, username);
    }

    public void removeSocket(String socketId) {
        if (!sockets.containsKey(socketId)) {
            logger.debug("Socket: " + socketId + " does not exist!");
            return;
        }
        sockets.remove(socketId);
    }
}
