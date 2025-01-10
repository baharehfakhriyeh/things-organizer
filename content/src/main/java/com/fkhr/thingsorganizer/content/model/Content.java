package com.fkhr.thingsorganizer.content.model;

<<<<<<< HEAD
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fkhr.thingsorganizer.common.util.EntityType;
import com.fkhr.thingsorganizer.content.util.ObjectIdSerializer;
=======
import com.fkhr.thingsorganizer.common.util.EntityType;
>>>>>>> d8afa708b0e5d3f821f70c8970c0e8ebb692ea4d
import jakarta.persistence.GeneratedValue;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class Content implements Serializable {
    @Id
    @GeneratedValue
    ObjectId id;
    String text;
    byte[] data;
    String name;
    String extention;
    EntityType ownerType;
    String ownerId;

    public Content(ObjectId id, String text, byte[] data, String name, String extention,
                   EntityType ownerType, String ownerId) {
        this.id = id;
        this.text = text;
        this.data = data;
        this.name = name;
        this.extention = extention;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
    }

    public Content() {
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
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
