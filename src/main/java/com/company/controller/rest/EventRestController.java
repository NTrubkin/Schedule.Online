package com.company.controller.rest;

import com.company.dao.api.EventDAO;
import com.company.dto.EventDTO;
import com.company.dto.converter.IEntityConverter;
import com.company.model.Event;
import com.company.service.sender.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/event")
public class EventRestController {

    private final EventDAO eventDAO;
    private final IEntityConverter<Event, EventDTO> converter;
    private final NotificationService notificationService;

    @Autowired
    public EventRestController(EventDAO eventDAO, IEntityConverter<Event, EventDTO> converter, NotificationService notificationService) {
        this.eventDAO = eventDAO;
        this.converter = converter;
        this.notificationService = notificationService;
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
    public ResponseEntity<EventDTO> readEvent(@PathVariable int eventId) {
        EventDTO result = converter.convert(eventDAO.read(eventId));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity createEvent(@RequestBody EventDTO eventDTO, @RequestParam(name = "notify", defaultValue = "true") boolean notify) {
        eventDAO.create(converter.restore(eventDTO));
        if (notify) {
            notificationService.sendScheduleNotifications(eventDTO.getGroupId());
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEvent(@PathVariable int eventId, @RequestParam(name = "notify", defaultValue = "true") boolean notify) {
        Event event = eventDAO.read(eventId);
        if (notify) {
            notificationService.sendScheduleNotifications(event.getGroup().getId());
        }
        eventDAO.delete(event);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> readEventsByGroup() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    ResponseEntity<List<EventDTO>> readEventsByGroup(int groupId) {
        List<Event> events = eventDAO.readAllByGroup(groupId);
        return new ResponseEntity<>(converter.convert(events), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity updateEvent(@RequestBody EventDTO eventDTO, @RequestParam(name = "notify", defaultValue = "true") boolean notify) {
        Event event = converter.restore(eventDTO);
        eventDAO.update(event);
        if (notify) {
            notificationService.sendScheduleNotifications(eventDTO.getGroupId());
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
