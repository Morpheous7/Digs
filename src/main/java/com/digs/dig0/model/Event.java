package com.digs.dig0.model;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Transactional
@Table(name = "Event", schema = "world")
@Getter
@Setter
public class Event extends BaseEntity {

    @JdbcTypeCode(SqlTypes.JSON)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "event_eventType", joinColumns = @JoinColumn(name = "event_id",referencedColumnName = "id" ), inverseJoinColumns = @JoinColumn(name = "event_name"))
    private Set<Event_type> event_type = new HashSet<>();
    private String event_Title;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinTable(name = "user_events", joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id" ), inverseJoinColumns = @JoinColumn(name = "event_id"))
   @JdbcTypeCode(SqlTypes.JSON)
   private User event_Organizer;
    private String event_Location;
    private Date eventStart;
    private Date eventStart_Time;
    private Date eventEnd;
    private Date eventEnd_Time;
    private boolean display_StartTime;
    private boolean single_Event;


}
