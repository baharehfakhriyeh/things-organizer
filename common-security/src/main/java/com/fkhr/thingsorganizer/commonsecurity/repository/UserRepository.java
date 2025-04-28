package com.fkhr.thingsorganizer.commonsecurity.repository;

import com.fkhr.thingsorganizer.commonsecurity.model.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    @Query(value = "select u.id, u.username, u.from_date as fromDate, u.expiration_time as expirationTime, u.active, u.role " +
            "from user_ u where u.username like :username", nativeQuery = true)
    Map<String, Object> findUserByUsernameNoCredentials(String username);
    boolean existsUserByUsername(String username);
    boolean existsUserByUsernameAndPassword(String username, String password);
    boolean existsUserByUsernameAndActiveIsTrue(String username);
    @Query(value = "select u.id, u.username, u.from_date as fromDate, u.expiration_time as expirationTime, u.active, u.role " +
            "from user_ u", nativeQuery = true)
    List<Map<String, Object>> findAllUsers(Pageable pageable);
    @Modifying
    @Query("Update User t set t.active = :active where t.username = :username")
    Integer updateActive(String username, boolean active);
    @Modifying
    @Query("Update User t set t.password = COALESCE(:password, t.password) where t.username = :username")
    Integer updatePassword(String username, String password);

    @Modifying
    @Query(value = "Update User t " +
            "set t.fromDate = COALESCE(:fromDate, t.fromDate), t.expirationTime = :expirationTime " +
            "where t.username = :username")
    void updateValidityDates(String username, Date fromDate, @Param("expirationTime") Date expirationTime);

    @Modifying
    @Query(value = "Update User t set t.role = COALESCE(:role, t.role) where t.username = :username")
    void updateRole(String username, String role);
}
