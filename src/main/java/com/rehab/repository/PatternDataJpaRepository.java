package com.rehab.repository;

import com.rehab.model.Pattern;

import java.util.List;

public class PatternDataJpaRepository {

    private PatternCrudRepository repository;

    public Pattern get(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Pattern> getAll() {
        return repository.findAll();
    }
}
