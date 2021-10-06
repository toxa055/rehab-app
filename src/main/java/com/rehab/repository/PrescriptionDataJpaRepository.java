package com.rehab.repository;

import com.rehab.model.Prescription;

import java.util.List;

public class PrescriptionDataJpaRepository {

    private PrescriptionCrudRepository repository;

    public Prescription get(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Prescription> getAll() {
        return repository.findAll();
    }
}
