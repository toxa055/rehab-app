package com.rehab.model;

import javax.persistence.*;

/**
 * General model class for any entity which will be mapped to table in database.
 * Any model class extends this one in order to have id.
 */
@MappedSuperclass
public abstract class AbstractIdEntity {

    /**
     * Unique identifier which has any entity.
     * It maps to column 'id' to table in database.
     */
    @Id
    @SequenceGenerator(name = "general_seq", sequenceName = "general_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "general_seq")
    protected Integer id;

    /**
     * Empty constructor. It's mandatory for Hibernate.
     */
    protected AbstractIdEntity() {
    }

    /**
     * Constructor to initialize following fields.
     *
     * @param id description of id is in field declaration.
     */
    public AbstractIdEntity(Integer id) {
        this.id = id;
    }

    /**
     * Method to get current value of id.
     *
     * @return current value of id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Method to set value for id.
     *
     * @param id incoming value for id.
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
