package com.rehab.repository;

import com.rehab.model.Cure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CureCrudRepository extends JpaRepository<Cure, Integer> {

    Optional<Cure> getByNameIgnoreCase(String name);
}
