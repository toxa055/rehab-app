package com.rehab.model;

import com.rehab.model.type.CureType;

import javax.persistence.*;

/**
 * Entity describing particular cure which is used for patient's treating.
 * Since it extends {@link AbstractIdEntity} it has id.
 * It maps to table 'cures' in database.
 */
@Entity
@Table(name = "cures")
public class Cure extends AbstractIdEntity {

    /**
     * Unique cure's name.
     * It maps to column 'name' to table 'cures' in database.
     * Value cannot be null.
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * Cure's type.
     * It maps to column 'cure_type' to table 'cures' in database.
     * Value cannot be null.
     *
     * @see CureType
     */
    @Column(name = "cure_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CureType cureType;

    /**
     * Empty constructor. It's mandatory for Hibernate.
     */
    public Cure() {
    }

    /**
     * Constructor to initialize following fields.
     *
     * @param id       description of id is in field declaration.
     * @param name     description of name is in field declaration.
     * @param cureType description of cureType is in field declaration.
     */
    public Cure(Integer id, String name, CureType cureType) {
        super(id);
        this.name = name;
        this.cureType = cureType;
    }

    public String getName() {
        return name;
    }

    public CureType getCureType() {
        return cureType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCureType(CureType cureType) {
        this.cureType = cureType;
    }
}
