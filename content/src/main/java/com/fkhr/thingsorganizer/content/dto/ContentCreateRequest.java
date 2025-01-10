package com.fkhr.thingsorganizer.content.dto;

import com.fkhr.thingsorganizer.common.util.EntityType;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class ContentCreateRequest implements Serializable {

    String text;
    MultipartFile data;
    String name;
    String extention;
    EntityType ownerType;
    String ownerId;

    public ContentCreateRequest(String text, MultipartFile data, String name, String extention,
                                EntityType ownerType, String ownerId) {
        this.text = text;
        this.data = data;
        this.name = name;
        this.extention = extention;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
    }

    public ContentCreateRequest() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MultipartFile getData() {
        return data;
    }

    public void setData(MultipartFile data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    public EntityType getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(EntityType ownerType) {
        this.ownerType = ownerType;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
