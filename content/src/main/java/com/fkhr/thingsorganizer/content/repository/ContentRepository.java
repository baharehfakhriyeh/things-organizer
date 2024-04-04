package com.fkhr.thingsorganizer.content.repository;

import com.fkhr.thingsorganizer.common.util.EntityType;
import com.fkhr.thingsorganizer.content.model.Content;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentRepository extends MongoRepository<Content, ObjectId> {
    @Query(value = "{'_id' : ?0 }", fields = "{ 'data' : 0 }")
    public Optional<Content> findContentInfoById(ObjectId id);
    @Query(value = "{'ownerType' : ?0, 'ownerId': ?1 }", fields = "{ 'data' : 0 }")
    List<Optional<Content>> findContentByOwnerTypeAndOwnerId(EntityType ownerType, String ownerId);
}
