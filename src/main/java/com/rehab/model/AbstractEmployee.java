package com.rehab.model;

public abstract class AbstractEmployee extends AbstractNamedEntity {
    protected String email;
    protected String password;
    protected Role role;

    protected AbstractEmployee() {
    }

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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
