package com.fkhr.thingsorganizer.commonsecurity.dto;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;

import java.util.Date;

public class UserGetDto {
    Long id;
    String username;
    Date fromDate;
    Date expirationTime;
    boolean active;
    String role;

    public UserGetDto() {
    }

    public UserGetDto(Long id, String username, Date fromDate, Date expirationTime, boolean active,
                      String role) {
        this.id = id;
        this.username = username;
        this.fromDate = fromDate;
        this.expirationTime = expirationTime;
        this.active = active;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
