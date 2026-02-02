package com.fkhr.thingsorganizer.thing.controller.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fkhr.thingsorganizer.thing.dto.GeometryDto;
import com.fkhr.thingsorganizer.thing.service.ContainerService;
import io.grpc.Context;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.*;

public class BaseWebsocketHandler extends TextWebSocketHandler {

    // Track per-session running tasks and cancellable contexts
    protected final ConcurrentMap<String, SessionState> sessions = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(session.getId(), new SessionState(session));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        SessionState state = sessions.get(session.getId());
        if (state == null || !session.isOpen()) return;

        state.cancelActiveStream();
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        SessionState state = sessions.get(session.getId());
        if (state != null) state.cancelActiveStream();
        //todo: log or send error info to client
        if (session.isOpen()) {
            session.close(CloseStatus.SERVER_ERROR);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        SessionState state = sessions.remove(session.getId());
        if (state != null)
            state.cancelActiveStream();
    }

    // Helper to keep per-session execution and cancellation
    static public class SessionState {
        final WebSocketSession session;
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        Context.CancellableContext cancellableContext;
        Future<?> activeTask;

        SessionState(WebSocketSession session) {
            this.session = session;
        }

        void startStream(Runnable runnable) {
            cancellableContext = Context.current().withCancellation();
            activeTask = executor.submit(() -> cancellableContext.run(runnable));
        }

        void cancelActiveStream() {
            if (cancellableContext != null) {
                cancellableContext.cancel(null);
                cancellableContext = null;
            }
            if (activeTask != null) {
                activeTask.cancel(true);
                activeTask = null;
            }
        }
    }
}
