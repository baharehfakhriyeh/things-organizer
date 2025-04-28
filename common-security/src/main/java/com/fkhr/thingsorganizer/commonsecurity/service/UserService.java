package com.fkhr.thingsorganizer.commonsecurity.service;

import com.fkhr.thingsorganizer.common.service.BaseService;
import com.fkhr.thingsorganizer.commonsecurity.dto.UserGetCurrentDto;
import com.fkhr.thingsorganizer.commonsecurity.dto.UserGetDto;
import com.fkhr.thingsorganizer.commonsecurity.dto.UserUpdatePasswordDto;
import com.fkhr.thingsorganizer.commonsecurity.dto.UserUpdateRoleDto;
import com.fkhr.thingsorganizer.commonsecurity.model.User;

import java.util.Date;
import java.util.List;

public interface UserService extends BaseService<User, Long> {
    boolean login(String username, String password);
    boolean existsUserByUsername(String username);
    boolean isUserActive(String username);
    List<UserGetDto> findAllUsers(int page, int size);
    void updateValidityDates(String username, Date fromDate, Date expirationTime);
    void updateRole(UserUpdateRoleDto userUpdateRoleDto);
    void updatePassword(UserUpdatePasswordDto userUpdatePasswordDto);
    void deactivate(String username);
    void activate(String username);
    UserGetDto loadByUsernameNoCredentials(String username);
    UserGetCurrentDto getCurrentUser();
}
