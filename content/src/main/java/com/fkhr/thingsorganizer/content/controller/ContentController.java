package com.fkhr.thingsorganizer.content.controller;

import com.fkhr.thingsorganizer.common.util.EntityType;
import com.fkhr.thingsorganizer.content.dto.ContentCreateRequest;
import com.fkhr.thingsorganizer.content.dto.ContentGetResponse;
import com.fkhr.thingsorganizer.content.model.Content;
import com.fkhr.thingsorganizer.content.service.ContentService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/content", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContentController {
    @Autowired
    private ContentService contentService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity createContent(@ModelAttribute ContentCreateRequest content) throws IOException {
        Content contentResult = contentService.upload(content);///todo: implement streaming
        return new ResponseEntity(contentResult, HttpStatus.OK);
    }

    @DeleteMapping(value = "/id/{id}")
    public ResponseEntity deleteContent(@PathVariable String id){
        contentService.delete(new ObjectId(id));
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/ownertype/{ownertype}/ownerid/{ownerid}")
    public ResponseEntity deleteContentByOwner(@PathVariable("ownertype") EntityType ownerType,
                                               @PathVariable("ownerid") String ownerId){
        Integer result = contentService.deleteByOwner(ownerType, ownerId);
        return new ResponseEntity(result == 0 ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping(value = "/info/id/{id}")
    public ResponseEntity getContentInfoById(@PathVariable String id){
        ContentGetResponse content = contentService.getById(new ObjectId(id));
        return new ResponseEntity(content, HttpStatus.OK);
    }

    @GetMapping(value = "/ownertype/{ownertype}/ownerid/{ownerid}")
    public ResponseEntity getAllContentsByOwner(@PathVariable("ownertype") EntityType ownerType,
                                                @PathVariable("ownerid") String ownerId){
        List<ContentGetResponse> contents = contentService.getAllByOwner(ownerType, ownerId);
        return new ResponseEntity(contents, HttpStatus.OK);
    }

    @GetMapping(value = "/id/{id}",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity download(@PathVariable String id){
        Content content = contentService.download(new ObjectId(id));///todo: implement streaming
        InputStreamResource resource = null;
        if(content.getData() != null) {
            InputStream inputStream = new ByteArrayInputStream(content.getData());
            resource = new InputStreamResource(inputStream);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + content.getName()+"."+content.getExtention() + "\"")
                .body(resource);
    }


}
