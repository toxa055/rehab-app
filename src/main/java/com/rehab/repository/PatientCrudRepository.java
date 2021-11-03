package com.rehab.repository;

import com.rehab.model.Patient;
import com.rehab.model.type.PatientState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public interface PatientCrudRepository extends JpaRepository<Patient, Integer> {

    @Query("""
            SELECT p FROM Patient p
            WHERE (:insuranceNumber IS NULL OR p.insuranceNumber=:insuranceNumber)
            AND (:nameLike IS NULL OR LOWER(p.name) LIKE %:nameLike%)
            AND (:treating IS NULL OR p.patientState=:treating)
            """)
    Page<Patient> filter(Integer insuranceNumber, String nameLike, PatientState treating, Pageable pageable);

    Optional<Patient> findByInsuranceNumber(int insuranceNumber);
}
