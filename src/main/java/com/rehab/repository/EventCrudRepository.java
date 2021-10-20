package com.rehab.repository;

import com.rehab.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventCrudRepository extends JpaRepository<Event, Integer> {

    @Query("SELECT e FROM Event e WHERE e.plannedDate=:plannedDate")
    List<Event> findAllToday(@Param("plannedDate") LocalDate plannedDate);

    List<Event> findAllByPatientId(int patientId);

    List<Event> findAllByNurseId(int nurseId);

    List<Event> findAllByPrescriptionId(int prescriptionId);
}
