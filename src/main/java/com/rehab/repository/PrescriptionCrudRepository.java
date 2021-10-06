package com.rehab.repository;

import com.rehab.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionCrudRepository extends JpaRepository<Prescription, Integer> {

}
