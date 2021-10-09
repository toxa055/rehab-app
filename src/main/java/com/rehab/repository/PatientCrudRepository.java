package com.rehab.repository;

import com.rehab.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientCrudRepository extends JpaRepository<Patient, Integer> {

    Patient getByInsuranceNumber(int insuranceNumber);
}
