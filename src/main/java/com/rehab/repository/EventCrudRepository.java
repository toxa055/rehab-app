package com.rehab.repository;

import com.rehab.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EventCrudRepository extends JpaRepository<Event, Integer> {

    List<Event> findAllByPlannedDate(LocalDate plannedDate);

    List<Event> findAllByPatientId(int patientId);

    @Query("SELECT e FROM Event e WHERE e.patient.insuranceNumber=:insuranceNumber")
    List<Event> findAllByInsuranceNumber(int insuranceNumber);

    @Query("SELECT e FROM Event e WHERE e.patient.insuranceNumber=:insuranceNumber AND e.plannedDate=:plannedDate")
    List<Event> findAllByInsuranceNumberAndPlannedDate(int insuranceNumber, LocalDate plannedDate);

    List<Event> findAllByNurseId(int nurseId);

    List<Event> findAllByPrescriptionId(int prescriptionId);
}
