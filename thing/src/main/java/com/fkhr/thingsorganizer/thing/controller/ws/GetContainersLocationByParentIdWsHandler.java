package com.fkhr.thingsorganizer.thing.controller.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fkhr.thingsorganizer.thing.dto.NullableIdDto;
import com.fkhr.thingsorganizer.thing.service.ContainerService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class GetContainersLocationByParentIdWsHandler extends BaseWebsocketHandler{
    private final ContainerService containerService;
    private final ObjectMapper objectMapper;

    public GetContainersLocationByParentIdWsHandler(ContainerService containerService, ObjectMapper objectMapper) {
        this.containerService = containerService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        SessionState state = sessions.get(session.getId());
        if (state == null || !session.isOpen()) return;
        state.cancelActiveStream();

        NullableIdDto parentIdDto = objectMapper.readValue(message.getPayload(), NullableIdDto.class);

        state.startStream(
                () -> containerService.getContainersLocationStreamByParentId(parentIdDto.getId(), session, state));
    }
}
