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
            "where case when :title is not null then (t.title = :title) else (1=1) end and " +
            "case when :weight is not null then (t.weight = :weight) else (1=1) end and " +
            "case when :containerId is not null then (t.container.id = :containerId) else (1=1) end and " +
            "case when :containerTitle is not null then (t.container.title = :containerTitle) else (1=1) end " +
            "order by t.title asc, c.id asc")
    List<Thing> searchThings(
            String title, Long weight, Long containerId, String containerTitle,
            Pageable pageable);
   /* List<Thing> searchThingsByTitleAndWeightAndContainer_IdAndContainer_TitleOrderByTitleAscContainerAsc(
            String title, Long weight, Long containerId, String containerTitle,
            Pageable pageable);*/
    @Modifying
    @Query("Update Thing t set t.container.id = :newContainerId where t.container.id = :oldContainerId " +
            "or (:oldContainerId is null and t.container.id is null)")
    Integer updateContainer(Long oldContainerId, Long newContainerId);
}
