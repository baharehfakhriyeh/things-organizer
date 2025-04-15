package com.fkhr.thingsorganizer.content.service;

import com.fkhr.thingsorganizer.common.exeptionhandling.CustomError;
import com.fkhr.thingsorganizer.common.exeptionhandling.CustomExeption;
import com.fkhr.thingsorganizer.common.util.EntityType;
import com.fkhr.thingsorganizer.content.dto.ContentCreateRequest;
import com.fkhr.thingsorganizer.content.dto.ContentGetResponse;
import com.fkhr.thingsorganizer.content.model.Content;
import com.fkhr.thingsorganizer.content.repository.ContentRepository;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ModelMapper modelMapper;
    final private ContentRepository contentRepository;

    @Autowired
    public ContentServiceImpl(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public Content save(Content content) {
        return null;
    }

    @Override
    public void delete(ObjectId id) {
        Content content = this.load(id);
        if (content == null) {
            throw new CustomExeption(CustomError.CONTENT_NOT_FOUND, HttpStatus.ACCEPTED);
        }
        contentRepository.delete(content);
    }

    @Override
    public Content load(ObjectId id) {
        Optional<Content> content = contentRepository.findContentInfoById(id);
        if (content.equals(Optional.empty())) {
            throw new CustomExeption(CustomError.CONTENT_NOT_FOUND, HttpStatus.ACCEPTED);
        }
        return content.get();
    }

    public ContentGetResponse getById(ObjectId id){
        Content content = load(id);
        ContentGetResponse contentGetResponse = modelMapper.map(content, ContentGetResponse.class);
        return contentGetResponse;
    }

    @Override
    public Integer deleteByOwner(EntityType ownerType, String ownerId) {
        Boolean contentsExist = contentRepository.countContentByOwnerTypeAndOwnerId(ownerType, ownerId);
        Integer result = 0;
        if(contentsExist) {
            result = contentRepository.deleteByOwnerTypeAndOwnerId(ownerType, ownerId);
        }
        return result;
    }

    @Override
    public List<Content> findAll() {
        return null;
    }

    @Override
    public List<Content> findAll(int page, int size) {
        return null;
    }

    @Override
    public boolean exists(ObjectId id) {
        if (this.load(id) != null)
            return true;
        else
            return false;
    }

    @Override
    public Content download(ObjectId id) {
        Optional<Content> contentOptional = contentRepository.findById(id);
        if(contentOptional.isEmpty()){
            throw new CustomExeption(CustomError.CONTENT_NOT_FOUND, HttpStatus.ACCEPTED);
        }
        return contentOptional.get();
    }

    @Override
    public List<ContentGetResponse> getAllByOwner(EntityType ownerType, String ownerId) {
        List<Optional<Content>> contentOptional = contentRepository.findContentByOwnerTypeAndOwnerId(ownerType, ownerId);
        List<ContentGetResponse> contents = null;
        if(contentOptional.size() > 0) {
            contents = contentOptional.stream().map(item -> modelMapper.map(item.get(), ContentGetResponse.class)).toList();
        }

        return contents;
    }

    @Override
    public Content upload(ContentCreateRequest contentCreateRequest) throws IOException {
        Content content = modelMapper.map(contentCreateRequest, Content.class);
        if(contentCreateRequest.getData() != null) {
            content.setData(contentCreateRequest.getData().getBytes());
        }
        content = contentRepository.save(content);
        return content;
    }
}
