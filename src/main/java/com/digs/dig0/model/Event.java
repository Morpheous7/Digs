package com.digs.dig0.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.lang.annotation.Annotation;
import java.time.LocalDateTime;

/**
 * Copyright to Digs LLC
 *
 * @author Ike Kennedy
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Event", schema = "world")
@Getter
@Setter
@ToString
public class Event extends BaseEntity implements Converter {

  /*  @JdbcTypeCode(SqlTypes.JSON)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "event_eventType", joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "event_name"))*/
    private String event_type;
    private String event_Title;

    /*@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "event_image", joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "imageData_id"))*/
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKeyColumn(name = "name")
    ImageData inputFile;
    /* @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_events", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "event_id"))*/
    @ManyToOne(fetch = FetchType.LAZY)
    private User event_Organizer;
    private String location;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private LocalDateTime eventStart_Time;
    private boolean single_Event;
    private boolean repetitive_Event;
    private boolean privateEvent;

    public Event(String event_Title, String event_type, User event_Organizer, MultipartFile imageData, String location, String address1,
                 String address2, String city, String state, String zip, String country, LocalDateTime eventStart_Time, boolean single_Event,
                 boolean repetitive_Event, boolean privateEvent) throws IOException {

        this.event_Title = event_Title;
        this.event_type = event_type;
        this.event_Organizer = event_Organizer;
        this.inputFile = new ImageData(imageData);
        this.location = location;
        this.address1= address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.eventStart_Time = eventStart_Time;
        this.single_Event = single_Event;
        this.repetitive_Event = repetitive_Event;
        this.privateEvent = privateEvent;
    }

    public Event(MultipartFile file, String eventTitle, User user, String location, LocalDateTime eventStartTime, String eventType, boolean singleEvent,
                 String address1, String address2, boolean privateEvent, String city, String zip, String state, String country, boolean repetitiveEvent) throws IOException {
        this.event_Title = eventTitle;
        this.event_type = eventType;
        this.event_Organizer = user;
        this.inputFile = new ImageData(file);
        this.location = location;
        this.address1= address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.eventStart_Time = eventStartTime;
        this.single_Event = singleEvent;
        this.repetitive_Event = repetitiveEvent;
        this.privateEvent = privateEvent;
    }

    public Event(MultipartFile file, String eventTitle, User user, String location, LocalDateTime eventStartTime, String eventType,
                 boolean singleEvent, String address1, String address2, boolean privateEvent, String city, String zip, String state, String country, Boolean o) throws IOException {
        this.event_Title = eventTitle;
        this.event_type = eventType;
        this.event_Organizer = user;
        this.inputFile = new ImageData(file);
        this.location = location;
        this.address1= address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.eventStart_Time = eventStartTime;
        this.single_Event = singleEvent;
        this.repetitive_Event = false;
        this.privateEvent = privateEvent;
    }


    /**
     * @return
     */
    @Override
    public boolean autoApply() {
        return false;
    }

    /**
     * Returns the annotation interface of this annotation.
     *
     * @return the annotation interface of this annotation
     * @apiNote Implementation-dependent classes are used to provide
     * the implementations of annotations. Therefore, calling {@link
     * Object#getClass getClass} on an annotation will return an
     * implementation-dependent class. In contrast, this method will
     * reliably return the annotation interface of the annotation.
     * @see Enum#getDeclaringClass
     */
    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
