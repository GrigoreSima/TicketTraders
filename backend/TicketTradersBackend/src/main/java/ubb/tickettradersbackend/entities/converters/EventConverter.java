package ubb.tickettradersbackend.entities.converters;

import org.springframework.core.convert.converter.Converter;
import ubb.tickettradersbackend.entities.Event;
import ubb.tickettradersbackend.entities.Ticket;
import ubb.tickettradersbackend.entities.dtos.EventDTO;

import java.util.ArrayList;
import java.util.List;

public class EventConverter implements Converter<Event, EventDTO> {
    @Override
    public EventDTO convert(Event source) {
        // Safe mapping for ticket types and counting remaining tickets
        int leftTickets = 0;
        List<String> types = new ArrayList<>();

        if (source.getTickets() != null) {
            leftTickets = source.getTickets().size();
            for (Ticket t : source.getTickets()) {
                if (t.getType() != null && !types.contains(t.getType())) {
                    types.add(t.getType());
                }
            }
        }

        return new EventDTO(
                source.getId(),
                source.getName(),
                source.getDescription(),
                source.getOrganizer() != null ? source.getOrganizer().getId() : null,
                source.getAddress(),
                source.getCoordinates(),
                source.getStartDate(),
                source.getEndDate(),
                source.getTags() != null ? new ArrayList<>(source.getTags()) : new ArrayList<>(),
                leftTickets,
                types,
                source.getPhotos() != null ? new ArrayList<>(source.getPhotos()) : new ArrayList<>()
        );
    }
}