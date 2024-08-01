package com.digs.dig0.repository;

import com.digs.dig0.model.Event;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */
@Repository
public interface EventDao extends JpaRepository<Event, Long> {
    Event findById(long id);

    Optional<Event> save(Optional<Event> event);

    <S extends Event> @NotNull S save(@org.jetbrains.annotations.NotNull S entity);

}
