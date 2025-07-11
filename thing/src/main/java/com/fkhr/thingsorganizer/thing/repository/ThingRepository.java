package com.fkhr.thingsorganizer.thing.repository;

import com.fkhr.thingsorganizer.thing.model.Thing;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ThingRepository extends JpaRepository<Thing, Long> {
    @Query("select t from Thing t inner join Container c on t.container.id = c.id  " +
            "where (:title is null or (t.title like %:title%)) and " +
            "(:weight is null or t.weight = :weight) and " +
            "(:containerId is null or t.container.id = :containerId) and " +
            "(:containerTitle is null or t.container.title = :containerTitle) " +
            "order by t.title asc, c.id asc")
    List<Thing> searchThings(
            String title, Long weight, Long containerId, String containerTitle,
            Pageable pageable);

    @Modifying
    @Query("Update Thing t set t.container.id = :newContainerId where t.container.id = :oldContainerId " +
            "or (:oldContainerId is null and t.container.id is null)")
    Integer updateContainer(Long oldContainerId, Long newContainerId);
}
