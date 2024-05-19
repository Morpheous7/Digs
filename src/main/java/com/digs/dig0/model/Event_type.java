package com.digs.dig0.model;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@Entity
@NoArgsConstructor
@Table(name = "Event_type")
@Getter
@Setter
@AllArgsConstructor
public class Event_type extends BaseEntity {

   //@OneToOne(fetch = FetchType.LAZY)
  // @ManyToOne(fetch = FetchType.LAZY/*, mappedBy = "event_typeId"*/)
//@JoinTable(name = "Event", joinColumns =@JoinColumn(name = "event_type",referencedColumnName = "event_typed"), inverseJoinColumns = @JoinColumn(name = "event_type"))
    @Column(length = 255, name ="event_name")
    public String event_type;

}
