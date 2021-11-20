package com.rehab.dto;

import java.util.Objects;

public class EmployeeDto {
    private Integer id;
    private String name;
    private String position;
    private String email;

    public Integer getId() {
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

    public void setId(Integer id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDto that = (EmployeeDto) o;
        return id.equals(that.id)
                && name.equals(that.name)
                && position.equals(that.position)
                && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, position, email);
    }
}
