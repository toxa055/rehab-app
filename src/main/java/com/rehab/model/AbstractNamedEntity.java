package com.rehab.model;

public abstract class AbstractNamedEntity extends AbstractIdEntity {
    protected final String name;

    public AbstractNamedEntity(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
