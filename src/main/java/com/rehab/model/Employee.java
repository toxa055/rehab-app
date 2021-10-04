package com.rehab.model;

import com.rehab.model.type.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Employee extends AbstractIdEntity {
    private String name;
    private String position;
    private String email;
    private String password;
    private Set<Role> roles;
    private List<Prescription> prescriptions = new ArrayList<>();

    public Employee() {
    }

    public Employee(Integer id, String name, String position, String email, String password, Set<Role> roles) {
        super(id);
        this.name = name;
        this.position = position;
        this.email = email;
        this.password = password;
        this.roles = roles;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
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

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
