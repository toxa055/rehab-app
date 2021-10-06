package com.rehab.repository;

import com.rehab.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentCrudRepository extends JpaRepository<Treatment, Integer> {

}
