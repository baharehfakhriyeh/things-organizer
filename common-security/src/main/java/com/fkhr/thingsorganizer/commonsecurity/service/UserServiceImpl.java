package com.fkhr.thingsorganizer.commonsecurity.service;

import com.fkhr.thingsorganizer.common.exeptionhandling.CustomError;
import com.fkhr.thingsorganizer.common.exeptionhandling.CustomExeption;
import com.fkhr.thingsorganizer.commonsecurity.dto.UserGetCurrentDto;
import com.fkhr.thingsorganizer.commonsecurity.dto.UserGetDto;
import com.fkhr.thingsorganizer.commonsecurity.dto.UserUpdatePasswordDto;
import com.fkhr.thingsorganizer.commonsecurity.dto.UserUpdateRoleDto;
import com.fkhr.thingsorganizer.commonsecurity.model.User;
import com.fkhr.thingsorganizer.commonsecurity.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public boolean login(String username, String password){
        validateUsername(username);
        validatePassword(password);
        User user = userRepository.findUserByUsername(username);
        if(username.equals(user.getUsername())){
            //todo:encode password
            if(passwordEncoder.matches(password, user.getPassword())){
                //todo: login
                return true;
            }
            else{
                throw new CustomExeption(CustomError.INCORRECT_USERNAME_PASSWORD, HttpStatus.ACCEPTED);
            }
        }
        else{
            throw new CustomExeption(CustomError.INCORRECT_USERNAME_PASSWORD, HttpStatus.ACCEPTED);
        }
    }

    @Override
    public User save(User user) {
        if(user.getId() == null){
            if(existsUserByUsername(user.getUsername())){
                throw new CustomExeption(CustomError.USER_ALREADY_EXISTS, HttpStatus.ACCEPTED);
            }
            else{
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user = userRepository.save(user);
            }
        }
        return user;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public User load(Long id) {
        Optional<User> userResult = userRepository.findById(id);
        User user = null;
        if(userResult != null){
            user = userResult.get();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findAll(int page, int size) {
        return null;
    }

    @Override
    public boolean exists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existsUserByUsername(String username) {
        return userRepository.existsUserByUsername(username);
    }

    @Override
    public List<UserGetDto> findAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        List<Map<String, Object>> resultList = userRepository.findAllUsers(pageable);
        List<UserGetDto> result = new ArrayList<>();
        if (resultList != null) {
            for (Map<String, Object> row : resultList) {
                result.add(mapResultToUserGetDto(row));
            }
        }
        return result;
    }

    @Override
    public boolean isUserActive(String username) {
        return userRepository.existsUserByUsernameAndActiveIsTrue(username);
    }

    public void updateValidityDates(String username, Date fromDate, Date expirationTime) {
        validateUsername(username);
        userRepository.updateValidityDates(username, fromDate, expirationTime);
    }

    @Override
    public void updateRole(UserUpdateRoleDto userUpdateRoleDto) {
        validateUsername(userUpdateRoleDto.getUsername());
        userRepository.updateRole(userUpdateRoleDto.getUsername(), userUpdateRoleDto.getRole());
    }

    @Override
    public void updatePassword(UserUpdatePasswordDto userUpdatePasswordDto) {
        validateUsername(userUpdatePasswordDto.getUsername());
        validatePassword(userUpdatePasswordDto.getOldPassword());
        validatePassword(userUpdatePasswordDto.getNewPassword());
       // String encodedOldPassword = passwordEncoder.encode(userUpdatePasswordDto.getOldPassword());
        User user = userRepository.findUserByUsername(userUpdatePasswordDto.getUsername());
        if(userUpdatePasswordDto.getUsername().equals(user.getUsername())) {
            if (passwordEncoder.matches(userUpdatePasswordDto.getOldPassword(),user.getPassword())) {
                //todo: implement with otp code
                userRepository.updatePassword(
                        userUpdatePasswordDto.getUsername(), passwordEncoder.encode(userUpdatePasswordDto.getNewPassword()));
            }
            else {
                throw new CustomExeption(CustomError.INCORRECT_USERNAME_PASSWORD, HttpStatus.ACCEPTED);
            }
        }
        else{
            throw new CustomExeption(CustomError.INCORRECT_USERNAME_PASSWORD, HttpStatus.ACCEPTED);
        }
    }

    @Override
    public void deactivate(String username) {
        validateUsername(username);
        userRepository.updateActive(username, false);
    }

    @Override
    public void activate(String username) {
        validateUsername(username);
        userRepository.updateActive(username, true);
    }

    public UserGetDto loadByUsernameNoCredentials(String username) {
        Map<String, Object> userResult = userRepository.findUserByUsernameNoCredentials(username);
        if(userResult == null || userResult.size() <= 0){
            throw new CustomExeption(CustomError.USER_NOT_FOUND, HttpStatus.ACCEPTED);
        }
        else{
            return mapResultToUserGetDto(userResult);
        }
    }

    public UserGetCurrentDto getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            UserGetCurrentDto userGetCurrentDto = new UserGetCurrentDto(
                    authentication.getName(),
                    authentication.getAuthorities().toArray()[0].toString());
            return userGetCurrentDto;
        }
        else{
            throw new CustomExeption(CustomError.NO_USER_LOGGED_IN, HttpStatus.ACCEPTED);
        }
    }

    private void validateUsername(String username) {
        if(!existsUserByUsername(username)){
            throw new CustomExeption(CustomError.USER_NOT_FOUND, HttpStatus.ACCEPTED);
        }
    }

    private void validatePassword(String password) {
        if(password == null){
            //todo: pass field name to message
            throw new CustomExeption(CustomError.REQUIRED_FIELD, HttpStatus.ACCEPTED);
        }
        else if(false){
            //todo: password regex check
        }
    }

    private UserGetDto mapResultToUserGetDto(Map<String, Object> userResult){
        UserGetDto userGetDto = new UserGetDto();
        userGetDto.setId((Long) userResult.get("id"));
        userGetDto.setUsername(userResult.get("username").toString());
        userGetDto.setFromDate((Date) userResult.get("fromDate"));
        userGetDto.setExpirationTime((Date) userResult.get("expirationTime"));
        userGetDto.setActive((Boolean) userResult.get("active"));
        userGetDto.setRole(userResult.get("role").toString());
        return userGetDto;
    }
}
