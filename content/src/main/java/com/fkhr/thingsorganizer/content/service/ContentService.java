package com.fkhr.thingsorganizer.content.service;

import com.fkhr.thingsorganizer.common.service.BaseService;
import com.fkhr.thingsorganizer.common.util.EntityType;
import com.fkhr.thingsorganizer.content.dto.ContentCreateRequest;
import com.fkhr.thingsorganizer.content.dto.ContentGetResponse;
import com.fkhr.thingsorganizer.content.model.Content;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface ContentService extends BaseService<Content, ObjectId> {
    Content download(ObjectId id);
    List<ContentGetResponse> getAllByOwner(EntityType ownerType, String ownerId);
    Content upload(ContentCreateRequest contentCreateRequest) throws IOException;
    public ContentGetResponse getById(ObjectId id);
}
