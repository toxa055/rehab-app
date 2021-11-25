package com.rehab.repository;

import com.rehab.model.Pattern;
import com.rehab.model.type.TimeUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatternCrudRepository extends JpaRepository<Pattern, Integer> {

    Optional<Pattern> findByCountAndUnitAndPatternUnits(Integer count, TimeUnit unit, String patternUnits);
}
