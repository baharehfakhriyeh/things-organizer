package com.fkhr.thingsorganizer.thing.job;

import com.fkhr.thingsorganizer.thing.service.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HouseKeeperJob {
    @Autowired
    ThingService thingService;
    @Value("${job.housekeeper.root-container.id}")
    Long rootContainer;

    @Scheduled(cron = "${job.housekeeper.cron}")
    public void gatheringThings(){
        System.out.println("How keeper gatheringThings job started");
        Integer countUpdated = thingService.updateContainer(null, rootContainer);
        System.out.println(countUpdated + " things updated.");
        System.out.println("How keeper gatheringThings job finished.");
    }
}
