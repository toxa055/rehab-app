package com.rehab.dto;

import com.rehab.model.type.Role;

import javax.validation.constraints.*;
import java.util.Set;

public class UserDto {
    private int id;
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 8, max = 50, message = "Length must be from 8 to 50 symbols")
    private String name;
    @NotBlank(message = "Position cannot be empty")
    @Size(min = 6, max = 30, message = "Length must be from 6 to 30 symbols")
    private String position;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Incorrect email")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 12, message = "Length must be from 6 to 12 symbols")
    private String password;
    @NotBlank(message = "Password confirmation cannot be empty")
    @Size(min = 6, max = 12, message = "Length must be from 6 to 12 symbols")
    private String confirmPassword;
    @NotEmpty(message = "No one role has been selected")
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
