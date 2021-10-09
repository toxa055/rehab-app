package com.rehab.dto;

public class EmployeeDto {
    private int id;
    private String name;
    private String position;
    private String email;
    private String password;

    public EmployeeDto() {
    }

    public EmployeeDto(int id, String name, String position, String email, String password) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.email = email;
        this.password = password;
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
}
