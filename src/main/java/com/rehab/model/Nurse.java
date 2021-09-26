package com.rehab.model;

public class Nurse extends AbstractEmployee {

    public Nurse(int id, String name, String email, String password) {
        super(id, name, email, password, Role.NURSE);
    }
}
