package com.rehab.service;

import com.rehab.dto.TreatmentDto;
import com.rehab.model.Employee;
import com.rehab.model.Treatment;
import com.rehab.model.type.Role;
import com.rehab.repository.TreatmentCrudRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TreatmentService {

    private final TreatmentCrudRepository treatmentCrudRepository;
    private final ModelMapper modelMapper;
    private final TypeMap<TreatmentDto, Treatment> typeMap;

    @Autowired
    public TreatmentService(TreatmentCrudRepository treatmentCrudRepository, ModelMapper modelMapper) {
        this.treatmentCrudRepository = treatmentCrudRepository;
        this.modelMapper = modelMapper;
        typeMap = modelMapper.createTypeMap(TreatmentDto.class, Treatment.class);
    }

    public Treatment save(TreatmentDto treatmentDto) {
        return treatmentCrudRepository.save(toEntity(treatmentDto));
    }

    public TreatmentDto getById(int id) {
        return toDto(treatmentCrudRepository.findById(id).get());
    }

    public List<TreatmentDto> getAllByPatientId(int patientId) {
        return treatmentCrudRepository
                .findAllByPatientId(patientId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TreatmentDto> getAllByDoctorId(int doctorId) {
        return treatmentCrudRepository
                .findAllByDoctorId(doctorId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TreatmentDto> getAll() {
        return treatmentCrudRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private TreatmentDto toDto(Treatment treatment) {
        return modelMapper.map(treatment, TreatmentDto.class);
    }

    private Treatment toEntity(TreatmentDto treatmentDto) {
        treatmentDto.setDoctorId(1);
        Employee doctor = new Employee();
        doctor.setRoles(Set.of(Role.DOCTOR));
        typeMap.addMappings(modelMapper -> modelMapper.map(src -> doctor, Treatment::setDoctor));
        return modelMapper.map(treatmentDto, Treatment.class);
    }
}
