package com.fkhr.thingsorganizer.commonsecurity.dto;

public class UserGetCurrentDto {
    String username;
    String role;

    public UserGetCurrentDto() {
    }

    public UserGetCurrentDto(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
