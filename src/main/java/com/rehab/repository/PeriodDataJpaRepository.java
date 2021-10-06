package com.rehab.repository;

import com.rehab.model.Period;

import java.util.List;

public class PeriodDataJpaRepository {

    private PeriodCrudRepository repository;

    public Period get(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Period> getAll() {
        return repository.findAll();
    }

}
