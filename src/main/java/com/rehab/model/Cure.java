package com.rehab.model;

public class Cure extends AbstractNamedEntity {
    private CureType cureType = CureType.MEDICINE;

    public Cure() {
    }

    public Cure(int id, String name, CureType cureType) {
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
