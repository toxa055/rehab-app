package com.rehab.repository;

import com.rehab.model.Cure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface which allows working with cures as entities in database.
 *
 * @see Cure
 */
@Repository
public interface CureCrudRepository extends JpaRepository<Cure, Integer> {

    /**
     * Method to get only cure by its name ignoring case.
     *
     * @param name particular cure name, must not be null.
     * @return optional value of found cure.
     */
    Optional<Cure> findByNameIgnoreCase(String name);

    /**
     * Method to get cures whose names contain value of nameLike ignoring case.
     *
     * @param nameLike not a particular cure name, but char sequence which cure names have to contain.
     * @param pageable interface that provides pagination.
     * @return page of all the cures if nameLike is null or only found cures.
     */
    @Query("SELECT c FROM Cure c WHERE :nameLike IS NULL OR LOWER(c.name) LIKE %:nameLike%")
    Page<Cure> filter(String nameLike, Pageable pageable);
}
