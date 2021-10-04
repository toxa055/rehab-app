package com.rehab.model;

import com.rehab.model.type.CureType;

public class Cure extends AbstractIdEntity {
    private String name;
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
