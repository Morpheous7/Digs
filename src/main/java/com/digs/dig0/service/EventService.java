package com.digs.dig0.service;
import com.digs.dig0.dto.EventDto;
import com.digs.dig0.model.Event;
import com.digs.dig0.model.ImageData;
import com.digs.dig0.model.User;
import com.digs.dig0.repository.EventDao;
import com.digs.dig0.repository.ImageDataDao;
import com.digs.dig0.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;


/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */
@Service
@Transactional
@AllArgsConstructor
public class EventService {

    private UserRepository userRepository;
    private EventDao eventDao;
    private ImageService imageService;
    private final FilesStorageService storageService;

    public ResponseEntity.BodyBuilder createEvent(EventDto event) throws IOException {
        if (event.getPrivateEvent() == null){
            event.setPrivateEvent(false);
        } else if (event.getSingle_Event() == null){
            event.setSingle_Event(false);
        } else if (event.getRepetitive_Event() == null) {
            event.setRepetitive_Event(false);
        }

        Event event1 = Event.builder().location(event.getLocation()).city(event.getCity()).state(event.getState())
                .address1(event.getAddress1())
                .address2(event.getAddress2())
                .event_Organizer(event.getEvent_Organizer())
                .event_Title(event.getEvent_Title())
                .country(event.getCountry())
                .zip(event.getZip())
                .inputFile((ImageData) event.getInputFile())
                .eventStart_Time(event.getEventStart_Time())
                .single_Event(event.getSingle_Event())
                .repetitive_Event(event.getRepetitive_Event())
                .event_type(event.getEvent_type())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        //storageService.save(event1.getInputFile());
        imageService.store(event1.getInputFile());
        eventDao.save(event1);
        //return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(event));
        return  ResponseEntity.status(HttpStatus.CREATED);
    }

    public ResponseEntity<String> createEvent(Event event) throws JsonProcessingException {
        Event event1 = Event.builder().location(event.getLocation()).city(event.getCity()).state(event.getState())
                .address1(event.getAddress1())
                .address2(event.getAddress2())
                .event_Organizer(event.getEvent_Organizer())
                .event_Title(event.getEvent_Title())
                .country(event.getCountry())
                .zip(event.getZip())
                .inputFile((ImageData) event.getInputFile())
                .eventStart_Time(event.getEventStart_Time())
                .single_Event(event.isSingle_Event())
                .repetitive_Event(event.isRepetitive_Event())
                .event_type(event.getEvent_type())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();

        eventDao.save(event1);
        return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(event1));
        //return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> getEventById(Event eventId) {
        eventDao.findById(eventId.getId());
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> addEvent(Event event) {
       //
        eventDao.save(event);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteById(Event eventId) {
        eventDao.deleteById(eventId.getId());
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

}
