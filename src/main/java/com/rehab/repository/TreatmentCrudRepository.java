package com.rehab.repository;

import com.rehab.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentCrudRepository extends JpaRepository<Treatment, Integer> {

    List<Treatment> findAllByPatientId(int patientId);

    List<Treatment> findAllByDoctorId(int doctorId);
}
