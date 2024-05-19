package com.digs.dig0.repository;

import com.digs.dig0.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */
@Repository
public interface EventDao extends JpaRepository<Event, Long> {

}
