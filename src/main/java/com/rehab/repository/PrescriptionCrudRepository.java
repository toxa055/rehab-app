package com.rehab.repository;

import com.rehab.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionCrudRepository extends JpaRepository<Prescription, Integer> {

    List<Prescription> findAllByPatientId(int patientId);

    List<Prescription> findAllByDoctorId(int doctorId);
}
