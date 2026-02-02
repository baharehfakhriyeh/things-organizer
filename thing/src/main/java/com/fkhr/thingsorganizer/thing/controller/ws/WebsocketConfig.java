package com.fkhr.thingsorganizer.thing.controller.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {
    private final GetContainersLocationInAreaWsHandler getContainersLocationInAreaWsHandler;
    private final GetContainersLocationByParentIdWsHandler getContainersLocationByParentIdWsHandler;

    public WebsocketConfig(GetContainersLocationInAreaWsHandler getContainersLocationInAreaWsHandler, GetContainersLocationByParentIdWsHandler getContainersLocationByParentIdWsHandler) {
        this.getContainersLocationInAreaWsHandler = getContainersLocationInAreaWsHandler;
        this.getContainersLocationByParentIdWsHandler = getContainersLocationByParentIdWsHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getContainersLocationInAreaWsHandler, "/ws/thing/containers/area/location-stream")
                .addHandler(getContainersLocationByParentIdWsHandler, "/ws/thing/containers/parent-id/location-stream")
                .setAllowedOrigins("*");

    }
}
