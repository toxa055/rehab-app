package com.rehab.model;

public abstract class AbstractIdEntity {
    protected final int id;

    public AbstractIdEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
