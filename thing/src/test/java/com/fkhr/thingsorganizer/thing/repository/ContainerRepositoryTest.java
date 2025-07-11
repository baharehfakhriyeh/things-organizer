package com.fkhr.thingsorganizer.thing.repository;

import com.fkhr.thingsorganizer.thing.model.Container;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ContainerRepositoryTest {
  /*  private final ContainerRepository containerRepository;

    ContainerRepositoryTest(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }*/

    @Autowired
    private ContainerRepository containerRepository;

    @MockBean
    private JwtDecoder jwtDecoder;

    private Container container;

    @BeforeEach
    public void setup(){
        container = new Container(
                "backpack",
                null
        );
    }

    @Test
    public void givenContainerObject_whenSave_thenReturnSavedContainer(){
        Container savedContainer = containerRepository.save(container);
        Assertions.assertThat(savedContainer).isNotNull();
        Assertions.assertThat(savedContainer.getId()).isGreaterThan(0);
    }

    @Test
    public void givenContainerId_whenFindById_thenReturnContainerObject(){
        containerRepository.save(container);
        Long containerId = container.getId();
        Optional<Container> containerResult = containerRepository.findById(containerId);
        Assertions.assertThat(containerResult).isNotNull();
    }

    @Test
    public void givenContainerList_whenFindAll_thenReturnContainerList(){
        Container container1 = new Container(
                "bag",
                null
        );
        containerRepository.save(container);
        containerRepository.save(container1);
        List<Container> containerList = containerRepository.findAll();
        Assertions.assertThat(containerList).isNotNull();
        Assertions.assertThat(containerList.size()).isEqualTo(2);
    }

    @Test
    public void givenContainerObject_whenUpdate_thenReturnUpdatedContainerObject(){
        containerRepository.save(container);
        Container savedContainer = containerRepository.findById(container.getId()).get();
        savedContainer.setTitle("purse");
        Container updatedContainer = containerRepository.save(savedContainer);
        Assertions.assertThat(updatedContainer.getTitle()).isEqualTo("purse");
    }

    @Test
    public void givenContainerId_whenDelete_thenRemoveEmployee(){
        containerRepository.save(container);
        containerRepository.delete(container);
        Optional<Container> containerResult = containerRepository.findById(container.getId());
        Assertions.assertThat(containerResult).isEmpty();
    }

}