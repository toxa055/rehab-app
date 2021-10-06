package com.rehab.repository;

import com.rehab.model.Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodCrudRepository extends JpaRepository<Period, Integer> {

}
