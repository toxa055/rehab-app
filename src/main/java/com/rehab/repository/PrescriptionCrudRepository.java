package com.rehab.repository;

import com.rehab.model.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface which allows working with prescriptions as entities in database.
 *
 * @see Prescription
 */
@Repository
public interface PrescriptionCrudRepository extends JpaRepository<Prescription, Integer> {

    /**
     * Method to get prescriptions filtering by given parameters.
     *
     * @param pDate           particular prescription date.
     * @param insuranceNumber patient insurance number.
     * @param doctorId        doctor id.
     * @param active          only active prescriptions or any.
     * @param pageable        interface that provides pagination.
     * @return page of found prescriptions by given parameters.
     */
    @Query("""
            SELECT p FROM Prescription p
            WHERE (cast(:pDate AS date) IS NULL OR p.date=:pDate)
            AND (:insuranceNumber IS NULL OR p.patient.insuranceNumber=:insuranceNumber)
            AND (:doctorId IS NULL OR p.doctor.id=:doctorId)
            AND (:active IS NULL OR p.isActive=:active)
            """)
    Page<Prescription> filter(LocalDate pDate, Integer insuranceNumber, Integer doctorId, Boolean active,
                              Pageable pageable);

    /**
     * Method to get all prescriptions for particular treatment by given treatment id.
     *
     * @param treatmentId treatment id, must not be null.
     * @param pageable    interface that provides pagination.
     * @return page of found prescriptions.
     */
    Page<Prescription> findAllByTreatmentId(int treatmentId, Pageable pageable);

    /**
     * Method to get all prescriptions for particular treatment by given treatment id.
     *
     * @param treatmentId treatment id, must not be null.
     * @return list of found prescriptions.
     */
    List<Prescription> findAllByTreatmentId(int treatmentId);
}
