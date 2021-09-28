package com.rehab.model;

public class Doctor extends AbstractEmployee {

    public Doctor() {
    }

    public Doctor(int id, String name, String email, String password) {
        super(id, name, email, password, Role.DOCTOR);
    }
}
