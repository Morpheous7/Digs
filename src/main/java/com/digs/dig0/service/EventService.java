package com.digs.dig0.service;
import com.digs.dig0.model.Event;
import com.digs.dig0.model.User;
import com.digs.dig0.repository.EventDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */
@Service
public class EventService {

    private EventDao eventDao;

    public EventService() {
    }


    public ResponseEntity<String> createEvent(Event event, User findUser) {
        //findUser.setEvent(event);

        eventDao.save(event);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }


    public ResponseEntity<String> getEventById(Event eventId) {
        eventDao.findById(eventId.getId());
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> addEvent(Event event, User findUser) {
       // findUser.setEvent(event);
        eventDao.save(event);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }


    public ResponseEntity<String> deleteById(Event eventId) {
        eventDao.deleteById(eventId.getId());
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

}
