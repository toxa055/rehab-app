package com.rehab.repository;

import com.rehab.model.Treatment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface which allows working with treatments as entities in database.
 *
 * @see Treatment
 */
@Repository
public interface TreatmentCrudRepository extends JpaRepository<Treatment, Integer> {

    /**
     * Method to get treatments filtering by given parameters.
     *
     * @param tDate           particular treatment date.
     * @param insuranceNumber patient insurance number.
     * @param doctorId        doctor id.
     * @param closed          only closed treatments or any.
     * @param pageable        interface that provides pagination.
     * @return page of found treatments by given parameters.
     */
    @Query("""
            SELECT t FROM Treatment t
            WHERE (cast(:tDate AS date) IS NULL OR t.date=:tDate)
            AND (:insuranceNumber IS NULL OR t.patient.insuranceNumber=:insuranceNumber)
            AND (:doctorId IS NULL OR t.doctor.id=:doctorId)
            AND (:closed IS NULL OR t.isClosed=:closed)
            """)
    Page<Treatment> filter(LocalDate tDate, Integer insuranceNumber, Integer doctorId, Boolean closed,
                           Pageable pageable);

    /**
     * Method to get all treatments for particular patient by given patient id.
     *
     * @param patientId patient id, must not be null.
     * @return list of found treatments.
     */
    List<Treatment> findAllByPatientId(int patientId);
}
