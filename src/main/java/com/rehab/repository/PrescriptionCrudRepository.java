package com.rehab.repository;

import com.rehab.model.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PrescriptionCrudRepository extends JpaRepository<Prescription, Integer> {

    @Query("""
            SELECT p FROM Prescription p
            WHERE (cast(:pDate AS date) IS NULL OR p.date=:pDate)
            AND (:insuranceNumber IS NULL OR p.patient.insuranceNumber=:insuranceNumber)
            AND (:doctorId IS NULL OR p.doctor.id=:doctorId)
            AND (:active IS NULL OR p.isActive=:active)
            """)
    Page<Prescription> filter(LocalDate pDate, Integer insuranceNumber, Integer doctorId, Boolean active,
                              Pageable pageable);

    List<Prescription> findAllByTreatmentId(int treatmentId);
}
