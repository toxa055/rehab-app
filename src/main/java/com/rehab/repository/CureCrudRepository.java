package com.rehab.repository;

import com.rehab.model.Cure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CureCrudRepository extends JpaRepository<Cure, Integer> {

    Optional<Cure> findByNameIgnoreCase(String name);

    @Query("SELECT c FROM Cure c WHERE :nameLike IS NULL OR LOWER(c.name) LIKE %:nameLike%")
    Page<Cure> filter(String nameLike, Pageable pageable);
}
