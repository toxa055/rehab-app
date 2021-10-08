package com.rehab.model;

import com.rehab.model.type.Role;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "employees")
public class Employee extends AbstractIdEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "employee_roles", joinColumns = @JoinColumn(name = "employee_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id", "role"}, name = "employee_roles_idx")})
    @JoinColumn(name = "employee_id")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
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
