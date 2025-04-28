package com.fkhr.thingsorganizer.commonsecurity.dto;

import com.fkhr.thingsorganizer.common.util.Utils;

import java.util.Date;

public class UserUpdateValidityDatesDto {
    String username;
    Date fromDate;
    Date expirationTime;

    public UserUpdateValidityDatesDto() {
        this.fromDate = Utils.getCurrentDate();
    }

    public UserUpdateValidityDatesDto(String username, Date fromDate, Date expirationTime) {
        this.username = username;
        this.fromDate = fromDate != null ? fromDate : Utils.getCurrentDate();
        this.expirationTime = expirationTime;
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
}
