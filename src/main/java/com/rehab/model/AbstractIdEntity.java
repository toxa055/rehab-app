package com.rehab.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractIdEntity {

    @Id
    @SequenceGenerator(name = "general_seq", sequenceName = "general_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "general_seq")
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
