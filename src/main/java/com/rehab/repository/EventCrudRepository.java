package com.rehab.repository;

import com.rehab.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EventCrudRepository extends JpaRepository<Event, Integer> {

    Page<Event> findAll(Pageable pageable);

    Page<Event> findAllByPlannedDate(LocalDate plannedDate, Pageable pageable);

    Page<Event> findAllByPatientId(int patientId, Pageable pageable);

    Page<Event> findAllByPatientInsuranceNumber(int insuranceNumber, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.patient.insuranceNumber=:insuranceNumber AND e.plannedDate=:plannedDate")
    Page<Event> findAllByInsuranceNumberAndPlannedDate(int insuranceNumber, LocalDate plannedDate, Pageable pageable);

    Page<Event> findAllByNurseId(int nurseId, Pageable pageable);

    List<Event> findAllByPrescriptionId(int prescriptionId);
}
