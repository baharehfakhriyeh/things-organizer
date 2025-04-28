package com.fkhr.thingsorganizer.commonsecurity.controller;

import com.fkhr.thingsorganizer.commonsecurity.dto.*;
import com.fkhr.thingsorganizer.commonsecurity.model.User;
import com.fkhr.thingsorganizer.commonsecurity.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(){
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "Creates a User")
    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody UserCreateDto userCreateDto){
        User user = new User();
        BeanUtils.copyProperties(userCreateDto, user);
        User resultUser = userService.save(user);
        return new ResponseEntity(resultUser, HttpStatus.OK);
    }

    @Operation(summary = "Updates validity data of a User.")
    @PostMapping(value = "/validity", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateValidityDates(@RequestBody UserUpdateValidityDatesDto userUpdateValidityDatesDto){
        User user = new User();
        BeanUtils.copyProperties(userUpdateValidityDatesDto, user);
        userService.updateValidityDates(
                user.getUsername(), user.getFromDate(), user.getExpirationTime());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "Gets a User.")
    @PostMapping(value = "/user/username/{username}")
    public ResponseEntity getUserByUsername(@PathVariable String username){
        UserGetDto userResult = userService.loadByUsernameNoCredentials(username);
        return new ResponseEntity(userResult, HttpStatus.OK);
    }

    @Operation(summary = "Updates password of a User.")
    @PreAuthorize("#userUpdatePasswordDto.username == authentication.name")
    @PostMapping(value = "/password",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updatePassword(@RequestBody UserUpdatePasswordDto userUpdatePasswordDto){
        userService.updatePassword(userUpdatePasswordDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "Activates a User.")
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping(value = "/user/activate/{username}")
    public ResponseEntity activateUser(@PathVariable String username){
        userService.activate(username);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "Deactivates a User.")
    @PreAuthorize("hasAuthority('admin') || #username == authentication.name")
    @PostMapping(value = "/user/deactivate/{username}")
    public ResponseEntity deactivateUser(@PathVariable String username){
        userService.deactivate(username);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "Updates role of a User.")
    @PostMapping(value = "/role", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateRole(@RequestBody UserUpdateRoleDto userUpdateRoleDto){
        User user = new User();
        BeanUtils.copyProperties(userUpdateRoleDto, user);
        userService.updateRole(userUpdateRoleDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "Gets all Users paginated.")
    @GetMapping()
    public ResponseEntity<List<UserGetDto>> getUserList(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "10") int size){
        List<UserGetDto> userGetDtoList = userService.findAllUsers(page, size);
        return new ResponseEntity(userGetDtoList, HttpStatus.OK);
    }

    @Operation(summary = "Gets current logged in username.")
    @GetMapping("/current")
    public ResponseEntity<List<UserGetDto>> getCurrentUsername(){
        UserGetCurrentDto user = userService.getCurrentUser();
        return new ResponseEntity(user, HttpStatus.OK);
    }


}
