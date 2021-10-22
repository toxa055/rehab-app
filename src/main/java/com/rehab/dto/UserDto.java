package com.rehab.dto;

import com.rehab.model.type.Role;

import java.util.Set;

public class UserDto {
    private int id;
    private String name;
    private String position;
    private String email;
    private String password;
    private String confirmPassword;
    private Set<Role> roles;

    public UserDto() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
