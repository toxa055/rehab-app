package com.rehab.repository;

import com.rehab.model.Cure;

import java.util.List;

public class CureDataJpaRepository {

    private CureCrudRepository repository;

    public Cure get(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Cure> getAll() {
        return repository.findAll();
    }

}
