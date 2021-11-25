package com.rehab.model;

import com.rehab.model.type.Role;

import javax.persistence.*;
import java.util.*;

/**
 * Entity describing employee of rehabilitation hospital which is a user of this application.
 * Since it extends {@link AbstractIdEntity} it has id.
 * It maps to table 'employees' in database.
 */
@Entity
@Table(name = "employees")
public class Employee extends AbstractIdEntity {

    /**
     * Employee's name, second name.
     * It maps to column 'name' to table 'employees' in database.
     * Value cannot be null.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Employee's job position.
     * It maps to column 'position' to table 'employees' in database.
     * Value cannot be null.
     */
    @Column(name = "position", nullable = false)
    private String position;

    /**
     * Employee's unique email address. It's used for logging to the application.
     * It maps to column 'email' to table 'employees' in database.
     * Value cannot be null.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Employee's password. It's used for logging to the application.
     * It maps to column 'password' to table 'employees' in database.
     * Value cannot be null.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Employee's roles. They're used for defining what functionality is available for current employee.
     * It maps to column 'role' to table 'employee_roles' in database.
     * Table 'employee_roles' connects to table 'employees' with 'employee_id'.
     * Value cannot be null.
     *
     * @see Role
     */
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "employee_roles", joinColumns = @JoinColumn(name = "employee_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id", "role"}, name = "employee_roles_idx")})
    @JoinColumn(name = "employee_id")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    /**
     * Empty constructor. It's mandatory for Hibernate.
     */
    public Employee() {
    }

    /**
     * Constructor to initialize following fields.
     *
     * @param id       description of id is in field declaration.
     * @param name     description of name is in field declaration.
     * @param position description of position is in field declaration.
     * @param email    description of email is in field declaration.
     * @param password description of password is in field declaration.
     * @param roles    description of roles is in field declaration.
     */
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
}
