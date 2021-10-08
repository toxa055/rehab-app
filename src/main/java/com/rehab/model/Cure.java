package com.rehab.model;

import com.rehab.model.type.CureType;

import javax.persistence.*;

@Entity
@Table(name = "cures")
public class Cure extends AbstractIdEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "cure_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CureType cureType;

    public Cure() {
    }

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
