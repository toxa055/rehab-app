package com.rehab.repository;

import com.rehab.model.Treatment;

import java.util.List;

public class TreatmentDataJpaRepository {

    private TreatmentCrudRepository repository;

    public Treatment get(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Treatment> getAll() {
        return repository.findAll();
    }
}
