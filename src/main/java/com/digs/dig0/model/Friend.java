package com.digs.dig0.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@Builder
@Entity
@Table(name = "friend", schema = "world")
@AllArgsConstructor
@NoArgsConstructor
public class Friend extends BaseEntity {
    private String username;
    private String phn;
    private String name;
    //private List<Event> events;


}
