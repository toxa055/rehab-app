package com.rehab.repository;

import com.rehab.model.Patient;
import com.rehab.model.type.PatientState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface which allows working with patients as entities in database.
 *
 * @see Patient
 */
@Repository
public interface PatientCrudRepository extends JpaRepository<Patient, Integer> {

    /**
     * Method to get patients filtering by given parameters.
     *
     * @param insuranceNumber particular insurance number.
     * @param nameLike        not a particular patient name, but char sequence which patient names have to contain.
     * @param treating        patient state: only treating or not.
     * @param pageable        interface that provides pagination.
     * @return page of found patients by given parameters.
     */
    @Query("""
            SELECT p FROM Patient p
            WHERE (:insuranceNumber IS NULL OR p.insuranceNumber=:insuranceNumber)
            AND (:nameLike IS NULL OR LOWER(p.name) LIKE %:nameLike%)
            AND (:treating IS NULL OR p.patientState=:treating)
            """)
    Page<Patient> filter(Integer insuranceNumber, String nameLike, PatientState treating, Pageable pageable);

    /**
     * Method to get only patient by given insurance number.
     *
     * @param insuranceNumber particular insurance number, must not be null.
     * @return optional value of found patient.
     */
    Optional<Patient> findByInsuranceNumber(int insuranceNumber);
}
