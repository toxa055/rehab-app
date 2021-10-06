package com.rehab.repository;

import com.rehab.model.Patient;

import java.util.List;

public class PatientDataJpaRepository {

    private PatientCrudRepository repository;

    public Patient get(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Patient> getAll() {
        return repository.findAll();
    }
}
