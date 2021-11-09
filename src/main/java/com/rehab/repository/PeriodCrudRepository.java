package com.rehab.repository;

import com.rehab.model.Period;
import com.rehab.model.type.TimeUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodCrudRepository extends JpaRepository<Period, Integer> {

    Optional<Period> findByCountAndUnit(Integer count, TimeUnit unit);
}
