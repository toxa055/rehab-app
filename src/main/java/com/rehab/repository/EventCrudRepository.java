package com.rehab.repository;

import com.rehab.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCrudRepository extends JpaRepository<Event, Integer> {

}
