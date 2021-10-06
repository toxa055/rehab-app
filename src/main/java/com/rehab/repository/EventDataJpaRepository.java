package com.rehab.repository;

import com.rehab.model.Event;

import java.util.List;

public class EventDataJpaRepository {

    private EventCrudRepository repository;

    public Event get(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Event> getAll() {
        return repository.findAll();
    }
}
