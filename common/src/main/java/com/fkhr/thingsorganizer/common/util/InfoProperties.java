package com.fkhr.thingsorganizer.common.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("info")
public class InfoProperties {
    private boolean visible;
    private String name;
    private String surname;
    private String role;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        if(visible == null){
            visible = false;
        }
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return this.role + ": " + this.name + " " + this.surname;
    }
}
