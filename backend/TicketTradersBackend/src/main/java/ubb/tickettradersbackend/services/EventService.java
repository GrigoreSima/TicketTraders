package ubb.tickettradersbackend.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ubb.tickettradersbackend.entities.Event;
import ubb.tickettradersbackend.entities.dtos.EventDTO;
import ubb.tickettradersbackend.entities.validators.EventValidator;
import ubb.tickettradersbackend.repositories.EventRepository;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;
    private final ConversionService conversionService;
    private final EventValidator eventValidator;

    public EventService(EventRepository eventRepository, ConversionService conversionService, EventValidator eventValidator) {
        this.eventRepository = eventRepository;
        this.conversionService = conversionService;
        this.eventValidator = eventValidator;
    }

    public EventDTO addEvent(EventDTO eventDTO) {
        log.info("Adding eventDTO {}", eventDTO);

        Event event = conversionService.convert(eventDTO, Event.class);

        if (event == null) {
            return null;
        }

        eventValidator.validateForSave(event);
        return conversionService.convert(eventRepository.save(event), EventDTO.class);
    }

    public void updateEvent(Long eventID, EventDTO eventDTO) {
        log.info("Updating event {}", eventID);

        Event event = conversionService.convert(eventDTO, Event.class);

        if (event == null) {
            return;
        }

        eventValidator.validateForModify(event);
        eventRepository.save(event);
    }

    public void removeEvent(Long eventID) {
        log.info("Removing event {}", eventID);
        eventRepository.deleteById(eventID);
    }

    public EventDTO findEvent(Long eventID) {
        log.info("Finding event {}", eventID);
        Event event = eventRepository.findById(eventID).orElseThrow();
        return conversionService.convert(event, EventDTO.class);
    }
}