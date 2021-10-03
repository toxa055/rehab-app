package com.rehab.model;

import com.rehab.model.type.CureType;

public class Cure extends AbstractNamedEntity {
    private CureType cureType;

    public Cure() {
    }

    public Cure(Integer id, String name, CureType cureType) {
        super(id, name);
        this.cureType = cureType;
    }

    public CureType getCureType() {
        return cureType;
    }

    public void setCureType(CureType cureType) {
        this.cureType = cureType;
    }
}
