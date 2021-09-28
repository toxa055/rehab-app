package com.rehab.model;

public class Admin extends AbstractEmployee {

    public Admin() {
    }

    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password, Role.ADMIN);
    }
}
