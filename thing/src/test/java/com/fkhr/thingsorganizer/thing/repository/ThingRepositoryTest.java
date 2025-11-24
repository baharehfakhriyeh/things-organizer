package com.fkhr.thingsorganizer.thing.repository;

import com.fkhr.thingsorganizer.thing.model.Container;
import com.fkhr.thingsorganizer.thing.model.Thing;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ThingRepositoryTest {
    @Autowired
    private ThingRepository thingRepository;
    @Autowired
    private ContainerRepository containerRepository;

    @MockBean
    private JwtDecoder jwtDecoder;

    private Thing thing;
    private Container container;
    private Pageable pageable;

    @BeforeEach
    public void setUp() {
        container = new Container(
                "red bag",
                null
        );
        containerRepository.save(container);
        thing = new Thing(null,
                "pen",
                5l,
                container
        );

        pageable = PageRequest.of(0, 10);
    }

    @Nested
    class SearchTest{
        private Container subContainer;
        private Thing thing2;
        @BeforeEach
        public void setup(){
            subContainer = new Container(
                    "yellow bag",
                    container
            );
            containerRepository.save(subContainer);
            thing2 = new Thing(null,
                    "pencil",
                    3l,
                    subContainer
            );
        }

        @Test
        void givenNoFilter_whenSearchThings_thenReturnThingsList() {
            thingRepository.save(thing);
            thingRepository.save(thing2);
            List<Thing> thingList = thingRepository.searchThings(null,
                    null, null, null, pageable);
            Assertions.assertThat(thingList).isNotNull();
            Assertions.assertThat(thingList.size()).isEqualTo(2);
        }

        @Test
        void givenAllFilters_whenSearchThings_thenReturnThingsList() {
            thingRepository.save(thing);
            thingRepository.save(thing2);
            List<Thing> thingList = thingRepository.searchThings(thing.getTitle(),
                    thing.getWeight(), thing.getContainer().getId(), thing.getContainer().getTitle(), pageable);
            Assertions.assertThat(thingList).isNotNull();
            Assertions.assertThat(thingList.size()).isEqualTo(1);
        }

        @Test
        void givenTitle_whenSearchThings_thenReturnThingsList() {
            thingRepository.save(thing);
            thingRepository.save(thing2);
            List<Thing> thingList = thingRepository.searchThings(thing.getTitle(),
                    null, null, null, pageable);
            Assertions.assertThat(thingList).isNotNull();
            Assertions.assertThat(thingList.size()).isEqualTo(2);
        }

        @Test
        void givenWeight_whenSearchThings_thenReturnThingsList() {
            thingRepository.save(thing);
            thingRepository.save(thing2);
            List<Thing> thingList = thingRepository.searchThings(null,
                    thing.getWeight(), null, null, pageable);
            Assertions.assertThat(thingList).isNotNull();
            Assertions.assertThat(thingList.size()).isEqualTo(1);
        }

        @Test
        void givenContainerId_whenSearchThings_thenReturnThingsList() {
            thingRepository.save(thing);
            thingRepository.save(thing2);
            List<Thing> thingList = thingRepository.searchThings(null,
                    null, thing.getContainer().getId(), null, pageable);
            Assertions.assertThat(thingList).isNotNull();
            Assertions.assertThat(thingList.size()).isEqualTo(1);
        }

        @Test
        void givenContainerTitle_whenSearchThings_thenReturnThingsList() {
            thingRepository.save(thing);
            thingRepository.save(thing2);
            List<Thing> thingList = thingRepository.searchThings(null,
                    null, null, thing.getContainer().getTitle(), pageable);
            Assertions.assertThat(thingList).isNotNull();
            Assertions.assertThat(thingList.size()).isEqualTo(1);
        }
    }

    @Test
    void givenOldAndNewContainerIds_whenUpdateContainer_thenReturnUpdatedThingCount() {
        thingRepository.save(thing);
        Container containerObj = new Container(
                "blue bag",
                null
        );
        containerRepository.save(container);
        int updatedCount = thingRepository.updateContainer(thing.getContainer().getId(),containerObj.getId());
        Assertions.assertThat(updatedCount).isEqualTo(1);
    }
}