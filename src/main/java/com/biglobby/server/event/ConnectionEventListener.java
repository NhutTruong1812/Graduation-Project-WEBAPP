package com.biglobby.server.event;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.biglobby.server.storage.SocketStorage;

@Component
public class ConnectionEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionEventListener.class);

    @EventListener
    public void handleConnectedEvent(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = getAttributeFromHeader(headerAccessor, "username");
        SocketStorage.getInstance().addSocket(headerAccessor.getSessionId(), username);
        logger.info("User connected -------------------: " + username);
    }

    @EventListener
    public void handleDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        logger.debug("User disconnected: " + headerAccessor.getSessionId());
        SocketStorage.getInstance().removeSocket(headerAccessor.getSessionId());
    }

    private String getAttributeFromHeader(StompHeaderAccessor accessor, String attribute) {
        GenericMessage<?> generic = (GenericMessage<?>) accessor
                .getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
        if (generic != null) {
            SimpMessageHeaderAccessor nativeAccessor = SimpMessageHeaderAccessor.wrap(generic);
            List<String> userIdValue = nativeAccessor.getNativeHeader(attribute);
            return (userIdValue == null) ? null : userIdValue.stream().findFirst().orElse(null);
        }
        return null;
    }
}
