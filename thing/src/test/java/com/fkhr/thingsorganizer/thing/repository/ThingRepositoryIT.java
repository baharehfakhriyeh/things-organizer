package com.fkhr.thingsorganizer.thing.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:sqlserver://host.docker.internal:1433;databaseName=things-organizer;encrypt=true;trustServerCertificate=true",
        "spring.datasource.username=sa",
        "spring.datasource.password=fakhr",
        "spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver",
        "spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect"
})
class ThingRepositoryIT {
    @Autowired
    ThingRepository thingRepository;

    @Test
    void updateContainer() {
        Integer result = thingRepository.updateContainer(5L, 6L);
        assertNotEquals(0, result);
    }
}