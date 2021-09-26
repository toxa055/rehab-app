package com.rehab.model;

public abstract class AbstractEmployee extends AbstractNamedEntity {
    protected final String email;
    protected final String password;
    protected final Role role;

    public AbstractEmployee(int id, String name, String email, String password, Role role) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
