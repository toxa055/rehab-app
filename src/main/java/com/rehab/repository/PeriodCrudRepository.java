package com.rehab.repository;

import com.rehab.model.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodCrudRepository extends JpaRepository<Period, Integer> {

}
