package com.rehab.repository;

import com.rehab.model.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatternCrudRepository extends JpaRepository<Pattern, Integer> {

}
