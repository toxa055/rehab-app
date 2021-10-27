package com.rehab.repository;

import com.rehab.model.Treatment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TreatmentCrudRepository extends JpaRepository<Treatment, Integer> {

    @Query("""
            SELECT t FROM Treatment t
            WHERE (cast(:tDate AS date) IS NULL OR t.date=:tDate)
            AND (:insuranceNumber IS NULL OR t.patient.insuranceNumber=:insuranceNumber)
            AND (:doctorId IS NULL OR t.doctor.id=:doctorId)
            AND (:closed IS NULL OR t.isClosed=:closed)
            """)
    Page<Treatment> filter(LocalDate tDate, Integer insuranceNumber, Integer doctorId, Boolean closed,
                           Pageable pageable);

    List<Treatment> findAllByPatientId(int patientId);
}
