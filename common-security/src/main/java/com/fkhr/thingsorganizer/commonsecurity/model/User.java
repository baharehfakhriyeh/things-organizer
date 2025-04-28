package com.fkhr.thingsorganizer.commonsecurity.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "User_")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    String password;
    Date fromDate;
    Date expirationTime;
    boolean active;
    String role;

    public User() {
    }

    public User(Long id, String username, String password, Date fromDate, Date expirationTime, boolean active,
                String role) {
        this.id = id;
        this.username = username;
        this.password = password;
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
