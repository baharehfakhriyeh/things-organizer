package com.fkhr.thingsorganizer.content.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fkhr.thingsorganizer.common.util.EntityType;
import com.fkhr.thingsorganizer.content.util.ObjectIdSerializer;
import org.bson.types.ObjectId;

import java.io.Serializable;

public class ContentGetResponse implements Serializable {

    @JsonSerialize(using = ObjectIdSerializer.class)
    ObjectId id;
    String text;
    String name;
    String extention;
    EntityType ownerType;
    String ownerId;

    public ContentGetResponse(ObjectId id, String text, String name, String extention,
                              EntityType ownerType, String ownerId) {
        this.id = id;
        this.text = text;
        this.name = name;
        this.extention = extention;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
    }

    public ContentGetResponse() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
