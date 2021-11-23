package com.rehab.data;

import com.rehab.dto.CureDto;
import com.rehab.model.type.CureType;

public class CureTestData {

    private CureTestData() {
    }

    public static CureDto getCureDto1() {
        var cureDto = new CureDto();
        cureDto.setId(1);
        cureDto.setName("test-cure1");
        cureDto.setCureType(CureType.MEDICINE);
        return cureDto;
    }

    public static CureDto getCureDto2() {
        var cureDto = new CureDto();
        cureDto.setId(2);
        cureDto.setName("test-cure2");
        cureDto.setCureType(CureType.PROCEDURE);
        return cureDto;
    }

    public static CureDto getNewCureDto() {
        var cureDto = new CureDto();
        cureDto.setName("test-cure3");
        cureDto.setCureType(CureType.MEDICINE);
        return cureDto;
    }
}
