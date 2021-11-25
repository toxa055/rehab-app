package com.rehab.dto;

import com.rehab.model.type.CureType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CureDto {
    private int id;
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 30, message = "Length must be from 3 to 30 symbols")
    private String name;
    private CureType cureType;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CureDto cureDto = (CureDto) o;
        return id == cureDto.id && Objects.equals(name, cureDto.name) && cureType == cureDto.cureType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cureType);
    }
}
