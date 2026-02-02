package com.fkhr.thingsorganizer.thing.controller.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fkhr.thingsorganizer.thing.dto.GeometryDto;
import com.fkhr.thingsorganizer.thing.service.ContainerService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class GetContainersLocationInAreaWsHandler extends BaseWebsocketHandler {
    private final ContainerService containerService;
    private final ObjectMapper objectMapper;

    public GetContainersLocationInAreaWsHandler(ContainerService containerService, ObjectMapper objectMapper) {
        this.containerService = containerService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        SessionState state = sessions.get(session.getId());
        if (state == null || !session.isOpen()) return;
        state.cancelActiveStream();

        GeometryDto geometryDto = objectMapper.readValue(message.getPayload(), GeometryDto.class);
        state.startStream(
                () -> containerService.getContainersInAreaStream(geometryDto, session, state));
    }



}
