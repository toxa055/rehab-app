package com.rehab.repository;

import com.rehab.model.Pattern;
import com.rehab.model.type.TimeUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface which allows working with patterns as entities in database.
 *
 * @see Pattern
 */
@Repository
public interface PatternCrudRepository extends JpaRepository<Pattern, Integer> {

    /**
     * Method to get only pattern by its count, time unit and pattern units.
     *
     * @param count        particular count, must not be null.
     * @param unit         particular time unit, must not be null.
     * @param patternUnits particular pattern units, must not be null.
     * @return optional value of found period.
     */
    Optional<Pattern> findByCountAndUnitAndPatternUnits(Integer count, TimeUnit unit, String patternUnits);
}
