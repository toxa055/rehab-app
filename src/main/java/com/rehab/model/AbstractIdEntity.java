package com.rehab.model;

public abstract class AbstractIdEntity {
    protected Integer id;

    protected AbstractIdEntity() {
    }

    public AbstractIdEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
