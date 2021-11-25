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

/**
 * Interface which allows working with events as entities in database.
 *
 * @see Event
 */
@Repository
public interface EventCrudRepository extends JpaRepository<Event, Integer> {

    /**
     * Method to get events filtering by given parameters.
     *
     * @param plannedDate     event planned date.
     * @param insuranceNumber patient insurance number.
     * @param nurseId         nurse id.
     * @param planned         only planned events or any.
     * @param pageable        interface that provides pagination.
     * @return page of found events by given parameters.
     */
    @Query("""
            SELECT e FROM Event e
            WHERE (cast(:plannedDate AS date) IS NULL OR e.plannedDate=:plannedDate)
            AND (:insuranceNumber IS NULL OR e.patient.insuranceNumber=:insuranceNumber)
            AND (:nurseId IS NULL OR e.nurse.id=:nurseId)
            AND (:planned IS NULL OR e.eventState=:planned)
            """)
    Page<Event> filter(LocalDate plannedDate, Integer insuranceNumber, Integer nurseId, EventState planned,
                       Pageable pageable);

    /**
     * Method to get all events for particular prescription by given prescription id.
     *
     * @param prescriptionId prescription id, must not be null.
     * @param pageable       interface that provides pagination.
     * @return page of found events.
     */
    Page<Event> findAllByPrescriptionId(int prescriptionId, Pageable pageable);

    /**
     * Method to get all events for particular prescription by given prescription id.
     *
     * @param prescriptionId prescription id, must not be null.
     * @return list of found events.
     */
    List<Event> findAllByPrescriptionId(int prescriptionId);

    /**
     * Method to get only planned events by event planned date.
     *
     * @param plannedDate event planned date, must not be null.
     * @return list of found events.
     */
    @Query("""
            SELECT e FROM Event e
            WHERE (e.plannedDate=:plannedDate) AND (e.eventState='PLANNED')
            ORDER BY plannedTime
            """)
    List<Event> findPlannedByPlannedDate(LocalDate plannedDate);
}
