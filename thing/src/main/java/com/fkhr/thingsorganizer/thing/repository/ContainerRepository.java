package com.fkhr.thingsorganizer.thing.repository;

import com.fkhr.thingsorganizer.thing.model.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {
    @Query(value = "Select * from Container c where (:parentId is not null and c.parent_id = :parentId)" +
            " or (:parentId is null and c.parent_id is null)  order by c.title", nativeQuery = true)
    List<Container> getContainersByParentId(Long parentId);
}
