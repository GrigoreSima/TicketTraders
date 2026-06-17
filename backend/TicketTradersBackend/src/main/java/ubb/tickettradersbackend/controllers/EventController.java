package ubb.tickettradersbackend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.tickettradersbackend.entities.dtos.EventDTO;
import ubb.tickettradersbackend.services.EventService;

@RestController
@RequestMapping("/events")
public class EventController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventDTO> addEvent(@RequestBody EventDTO eventDTO) {
        log.info("Adding event {}", eventDTO);
        return ResponseEntity.ok(eventService.addEvent(eventDTO));
    }

    @PatchMapping("/{eventID}")
    public ResponseEntity<?> updateEvent(@PathVariable Long eventID, @RequestBody EventDTO eventDTO) {
        log.info("Updating event {}", eventID);
        eventService.updateEvent(eventID, eventDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{eventID}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventID) {
        log.info("Deleting event {}", eventID);
        eventService.removeEvent(eventID);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{eventID}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable Long eventID) {
        log.info("Getting event {}", eventID);
        return ResponseEntity.ok(eventService.findEvent(eventID));
    }
}