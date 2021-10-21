package com.rehab.repository;

import com.rehab.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrescriptionCrudRepository extends JpaRepository<Prescription, Integer> {

    List<Prescription> findAllByPatientId(int patientId);

    List<Prescription> findAllByDoctorId(int doctorId);

    @Query("SELECT p FROM Prescription p WHERE p.treatment.id=:treatmentId")
    List<Prescription> findAllByTreatmentId(int treatmentId);
}
