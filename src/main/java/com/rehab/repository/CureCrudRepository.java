package com.rehab.repository;

import com.rehab.model.Cure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CureCrudRepository extends JpaRepository<Cure, Integer> {

    Optional<Cure> findByNameIgnoreCase(String name);
}
