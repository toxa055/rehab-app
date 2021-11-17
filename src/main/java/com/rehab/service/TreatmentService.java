package com.rehab.service;

import com.rehab.dto.TreatmentDto;
import com.rehab.exception.ApplicationException;
import com.rehab.model.Prescription;
import com.rehab.model.Treatment;
import com.rehab.model.type.PatientState;
import com.rehab.repository.*;
import com.rehab.util.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
public class TreatmentService {

    private final TreatmentCrudRepository treatmentCrudRepository;
    private final PatientCrudRepository patientCrudRepository;
    private final PrescriptionCrudRepository prescriptionCrudRepository;
    private final ModelMapper modelMapper;
    private final TypeMap<TreatmentDto, Treatment> typeMap;

    @Autowired
    public TreatmentService(TreatmentCrudRepository treatmentCrudRepository, PatientCrudRepository patientCrudRepository,
                            PrescriptionCrudRepository prescriptionCrudRepository, ModelMapper modelMapper) {
        this.treatmentCrudRepository = treatmentCrudRepository;
        this.patientCrudRepository = patientCrudRepository;
        this.prescriptionCrudRepository = prescriptionCrudRepository;
        this.modelMapper = modelMapper;
        typeMap = modelMapper.createTypeMap(TreatmentDto.class, Treatment.class);
    }

    public TreatmentDto getById(int id) {
        return toDto(getTreatmentById(id));
    }

    @Transactional
    public TreatmentDto save(TreatmentDto treatmentDto) {
        var patientId = treatmentDto.getPatientId();
        var patient = patientCrudRepository.findById(patientId).orElseThrow(() ->
                new NoSuchElementException("Patient with id " + patientId + " not found."));
        if (patient.getPatientState() == PatientState.DISCHARGED) {
            patient.setPatientState(PatientState.TREATING);
            patientCrudRepository.save(patient);
        }
        return toDto(treatmentCrudRepository.save(toEntity(treatmentDto)));
    }

    @Transactional
    public TreatmentDto close(int id) {
        var treatmentForClosing = getTreatmentById(id);
        if (treatmentForClosing.isClosed()) {
            throw new ApplicationException("Treatment is already closed.");
        }
        var authDoctor = SecurityUtil.getAuthEmployee();
        if (!treatmentForClosing.getDoctor().getId().equals(authDoctor.getId())) {
            throw new ApplicationException("Cannot close treatment which was created by a different doctor.");
        }
        var hasActivePrescriptions = prescriptionCrudRepository.findAllByTreatmentId(id)
                .stream()
                .anyMatch(Prescription::isActive);
        if (hasActivePrescriptions) {
            throw new ApplicationException("Cannot close treatment which has active prescriptions.");
        }
        treatmentForClosing.setClosed(true);
        treatmentForClosing.setCloseDate(LocalDate.now());
        return toDto(treatmentCrudRepository.save(treatmentForClosing));
    }

    public Page<TreatmentDto> filter(LocalDate date, Integer insuranceNumber, boolean authDoctor, boolean onlyOpen,
                                     Pageable pageable) {
        return treatmentCrudRepository.filter(date, insuranceNumber,
                authDoctor ? SecurityUtil.getAuthEmployee().getId() : null,
                onlyOpen ? false : null,
                pageable).map(this::toDto);
    }

    private Treatment getTreatmentById(int id) {
        return treatmentCrudRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Treatment with id " + id + " not found."));
    }

    private TreatmentDto toDto(Treatment treatment) {
        return modelMapper.map(treatment, TreatmentDto.class);
    }

    private Treatment toEntity(TreatmentDto treatmentDto) {
        var authDoctor = SecurityUtil.getAuthEmployee();
        treatmentDto.setDoctorId(authDoctor.getId());
        treatmentDto.setDoctorName(authDoctor.getName());
        typeMap.addMappings(modelMapper -> modelMapper.map(src -> authDoctor, Treatment::setDoctor));
        return modelMapper.map(treatmentDto, Treatment.class);
    }
}
