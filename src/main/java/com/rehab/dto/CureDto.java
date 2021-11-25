package com.rehab.dto;

import com.rehab.model.type.CureType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Data Transfer Object for {@link com.rehab.model.Cure}, i.e. it's a view representation of Cure model.
 * It's used for transferring data between services and controllers.
 */
public class CureDto {

    /**
     * Cure id.
     */
    private int id;

    /**
     * Cure name.
     */
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 30, message = "Length must be from 3 to 30 symbols")
    private String name;

    /**
     * Cure type.
     */
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

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the o argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CureDto that = (CureDto) o;
        return id == that.id
                && name.equals(that.name)
                && cureType == that.cureType;
    }

    /**
     * Returns a hash code value for current object.
     *
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, cureType);
    }
}
