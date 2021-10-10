package com.rehab.dto;

import com.rehab.model.type.CureType;

public class CureDto {
    private int id;
    private String name;
    private CureType cureType;

    public CureDto() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CureType getCureType() {
        return cureType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCureType(CureType cureType) {
        this.cureType = cureType;
    }
}
