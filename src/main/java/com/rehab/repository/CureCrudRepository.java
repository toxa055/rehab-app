package com.rehab.repository;

import com.rehab.model.Cure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CureCrudRepository extends JpaRepository<Cure, Integer> {

    Cure getByName(String name);
}
