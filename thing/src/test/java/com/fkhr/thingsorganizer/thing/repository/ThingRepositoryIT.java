package com.fkhr.thingsorganizer.thing.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ThingRepositoryIT {
    @Autowired
    ThingRepository thingRepository;

    @Test
    void updateContainer() {
        Integer result = thingRepository.updateContainer(5L, 6L);
        assertNotEquals(0, result);
    }
}