package com.rehab.repository;

import com.rehab.model.Period;
import com.rehab.model.type.TimeUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface which allows working with periods as entities in database.
 *
 * @see Period
 */
@Repository
public interface PeriodCrudRepository extends JpaRepository<Period, Integer> {

    /**
     * Method to get only period by its count and time unit.
     *
     * @param count particular count, must not be null.
     * @param unit  particular time unit, must not be null.
     * @return optional value of found period.
     */
    Optional<Period> findByCountAndUnit(Integer count, TimeUnit unit);
}
