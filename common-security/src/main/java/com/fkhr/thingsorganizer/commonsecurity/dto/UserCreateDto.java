package com.fkhr.thingsorganizer.commonsecurity.dto;

import com.fkhr.thingsorganizer.common.util.Utils;

import java.time.LocalDate;
import java.util.Date;

public class UserCreateDto {
    String username;
    String password;
    Date fromDate;
    Date expirationTime;
    boolean active;
    String role;

    public UserCreateDto() {
        this.fromDate = Utils.getCurrentDate();
    }

    public UserCreateDto(String username, String password, Date fromDate, Date expirationTime, boolean active,
                         String role) {
        this.username = username;
        this.password = password;
        this.fromDate = fromDate != null ? fromDate : Utils.getCurrentDate();
        this.expirationTime = expirationTime;
        this.active = active;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
