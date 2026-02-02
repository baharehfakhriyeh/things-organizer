package com.fkhr.thingsorganizer.thing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fkhr.thingsorganizer.common.service.BaseService;
import com.fkhr.thingsorganizer.thing.controller.ws.BaseWebsocketHandler;
import com.fkhr.thingsorganizer.thing.dto.ContainerLocationDto;
import com.fkhr.thingsorganizer.thing.dto.GeometryDto;
import com.fkhr.thingsorganizer.thing.model.Container;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.UUID;

public interface ContainerService extends BaseService<Container, Long> {
    List<Container> search(Container container, int page, int size);
    List<Container> getByParentId(Long parentId);
    UUID updateLocation(long containerId, GeometryDto geometry) throws JsonProcessingException, InvalidProtocolBufferException;
    ContainerLocationDto getContainerLocation(long containerId);
    SseEmitter getContainersInAreaStream(GeometryDto geometryDto) throws InvalidProtocolBufferException, JsonProcessingException;
    void getContainersInAreaStream(GeometryDto geometryDto, WebSocketSession session,
                                   BaseWebsocketHandler.SessionState state);
    List<ContainerLocationDto> getContainersLocationStreamByParentId(Long parentId);
    void getContainersLocationStreamByParentId(Long parentId, WebSocketSession session,
                                               BaseWebsocketHandler.SessionState state);

    SseEmitter getContainerLocationStream(long containerId);
    SseEmitter getContainerLocationHistoryStream(long containerId);

}
