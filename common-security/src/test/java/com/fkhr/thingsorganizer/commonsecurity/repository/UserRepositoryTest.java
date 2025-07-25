package com.fkhr.thingsorganizer.commonsecurity.repository;

import com.fkhr.thingsorganizer.commonsecurity.config.TestConfig;
import com.fkhr.thingsorganizer.commonsecurity.model.User;
import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {TestConfig.class})
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @MockBean
    private JwtDecoder jwtDecoder;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(
                null,
                "bahareh",
                "123",
                DateTime.now().toDate(),
                null,
                true,
                "user"
        );
    }

    @Test
    void givenUsername_whenFindUserByUsername_thenReturnUser() {
        String username = "bahareh";
        userRepository.save(user);
        User result = userRepository.findUserByUsername(username);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(user);
    }

    @Test
    void givenUsername_whenFindUserByUsernameNoCredentials_thenReturnUserWithoutPassword() {
        String username = "bahareh";
        userRepository.save(user);
        Map<String, Object> result = userRepository.findUserByUsernameNoCredentials(username);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.get("username")).isEqualTo(user.getUsername());
        Assertions.assertThat(result.keySet().contains("password")).isFalse();
    }
}