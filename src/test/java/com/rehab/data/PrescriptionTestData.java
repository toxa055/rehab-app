package com.rehab.data;

import com.rehab.dto.*;
import com.rehab.model.Pattern;
import com.rehab.model.Period;

import java.time.LocalDate;
import java.util.List;

public class PrescriptionTestData {

    private static final TreatmentDto treatment1 = TreatmentTestData.getTreatmentDto1();
    private static final TreatmentDto treatment2 = TreatmentTestData.getTreatmentDto3();

    private static final CureDto cure1 = CureTestData.getCureDto1();
    private static final CureDto cure2 = CureTestData.getCureDto2();

    private static final Period period1 = PeriodTestData.getPeriod1();
    private static final Period period2 = PeriodTestData.getPeriod2();

    private static final Pattern pattern1 = PatternTestData.getPattern1();
    private static final Pattern pattern2 = PatternTestData.getPattern2();

    private PrescriptionTestData() {
    }

    public static PrescriptionDtoOut getPrescriptionDtoOut1() {
        var p = new PrescriptionDtoOut();
        p.setPatientId(treatment1.getPatientId());
        p.setPatientInsuranceNumber(treatment1.getPatientInsuranceNumber());
        p.setPatientName(treatment1.getPatientName());
        p.setDoctorId(treatment1.getDoctorId());
        p.setDoctorName(treatment1.getDoctorName());
        p.setTreatmentId(treatment1.getId());
        p.setCureId(cure1.getId());
        p.setCureName(cure1.getName());
        p.setCureType(cure1.getCureType());
        p.setPeriodId(period1.getId());
        p.setPeriodCount(period1.getCount());
        p.setPeriodUnit(period1.getUnit());
        p.setPatternId(pattern1.getId());
        p.setPatternCount(pattern1.getCount());
        p.setPatternUnit(pattern1.getUnit());
        p.setPatternUnits(pattern1.getPatternUnits());
        p.setDose("dose 1");
        p.setDate(LocalDate.now());
        p.setActive(true);
        return p;
    }

    public static PrescriptionDtoOut getPrescriptionDtoOut2() {
        var p = new PrescriptionDtoOut();
        p.setPatientId(treatment2.getPatientId());
        p.setPatientInsuranceNumber(treatment2.getPatientInsuranceNumber());
        p.setPatientName(treatment2.getPatientName());
        p.setDoctorId(treatment2.getDoctorId());
        p.setDoctorName(treatment2.getDoctorName());
        p.setTreatmentId(treatment2.getId());
        p.setCureId(cure2.getId());
        p.setCureName(cure2.getName());
        p.setCureType(cure2.getCureType());
        p.setPeriodId(period2.getId());
        p.setPeriodCount(period2.getCount());
        p.setPeriodUnit(period2.getUnit());
        p.setPatternId(pattern2.getId());
        p.setPatternCount(pattern2.getCount());
        p.setPatternUnit(pattern2.getUnit());
        p.setPatternUnits(pattern2.getPatternUnits());
        p.setDose("According to instruction.");
        p.setDate(LocalDate.now());
        p.setActive(true);
        return p;
    }

    public static PrescriptionDto getPrescriptionDto1() {
        var p = new PrescriptionDto();
        p.setPatientId(treatment1.getPatientId());
        p.setPatientInsuranceNumber(treatment1.getPatientInsuranceNumber());
        p.setPatientName(treatment1.getPatientName());
        p.setDoctorId(treatment1.getDoctorId());
        p.setDoctorName(treatment1.getDoctorName());
        p.setTreatmentId(treatment1.getId());
        p.setCureId(cure1.getId());
        p.setCureName(cure1.getName());
        p.setCureType(cure1.getCureType());
        p.setPeriodId(period1.getId());
        p.setPeriodCount(period1.getCount());
        p.setPeriodUnit(period1.getUnit());
        p.setPatternId(pattern1.getId());
        p.setPatternCount(pattern1.getCount());
        p.setPatternUnit(pattern1.getUnit());
        p.setPatternUnits(List.of("MORNING", "NIGHT"));
        p.setDose("dose 1");
        return p;
    }

    public static PrescriptionDto getPrescriptionDto2() {
        var p = new PrescriptionDto();
        p.setPatientId(treatment2.getPatientId());
        p.setPatientInsuranceNumber(treatment2.getPatientInsuranceNumber());
        p.setPatientName(treatment2.getPatientName());
        p.setDoctorId(treatment2.getDoctorId());
        p.setDoctorName(treatment2.getDoctorName());
        p.setTreatmentId(treatment2.getId());
        p.setCureId(cure2.getId());
        p.setCureName(cure2.getName());
        p.setCureType(cure2.getCureType());
        p.setPeriodId(period2.getId());
        p.setPeriodCount(period2.getCount());
        p.setPeriodUnit(period2.getUnit());
        p.setPatternId(pattern2.getId());
        p.setPatternCount(pattern2.getCount());
        p.setPatternUnit(pattern2.getUnit());
        p.setPatternUnits(List.of("MONDAY", "WEDNESDAY", "FRIDAY"));
        p.setDose("According to instruction.");
        return p;
    }
}
