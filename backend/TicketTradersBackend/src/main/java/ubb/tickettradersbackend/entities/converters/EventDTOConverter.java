package ubb.tickettradersbackend.entities.converters;

import org.springframework.core.convert.converter.Converter;
import ubb.tickettradersbackend.entities.Event;
import ubb.tickettradersbackend.entities.dtos.EventDTO;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class EventDTOConverter implements Converter<EventDTO, Event> {
    @Override
    public Event convert(EventDTO source) {
        Event event = new Event();
        event.setId(source.id());
        event.setName(source.name());
        event.setDescription(source.description());
        event.setAddress(source.address());
        event.setCoordinates(source.coordinates());
        event.setStartDate(source.startDateTime());
        event.setEndDate(source.endDateTime());
        event.setTags(source.tags() != null ? new ArrayList<>(source.tags()) : new ArrayList<>());
        event.setPhotos(source.photos() != null ? new ArrayList<>(source.photos()) : new ArrayList<>());
        return event;
    }
}