package com.rehab.repository;

import com.rehab.model.Event;
import com.rehab.model.type.EventState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventCrudRepository extends JpaRepository<Event, Integer> {

    @Query("""
            SELECT e FROM Event e
            WHERE (cast(:plannedDate AS date) IS NULL OR e.plannedDate=:plannedDate)
            AND (:insuranceNumber IS NULL OR e.patient.insuranceNumber=:insuranceNumber)
            AND (:nurseId IS NULL OR e.nurse.id=:nurseId)
            AND (:planned IS NULL OR e.eventState=:planned)
            """)
    Page<Event> filter(LocalDate plannedDate, Integer insuranceNumber, Integer nurseId, EventState planned,
                       Pageable pageable);

    Page<Event> findAllByPrescriptionId(int prescriptionId, Pageable pageable);

    List<Event> findAllByPrescriptionId(int prescriptionId);

    @Query("""
            SELECT e FROM Event e
            WHERE (e.plannedDate=:plannedDate) AND (e.eventState='PLANNED')
            ORDER BY plannedTime
            """)
    List<Event> findAllTodayPlanned(LocalDate plannedDate);
}
