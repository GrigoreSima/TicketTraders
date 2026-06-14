package ubb.tickettradersbackend.entities.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record EventDTO(
        Long id,
        String name,
        String description,
        Long organizerId,
        String address,
        String coordinates,
        LocalDateTime startDateTime, // Matched with "startDateTime" from requirements
        LocalDateTime endDateTime,   // Matched with "endDateTime" from requirements
        List<String> tags,
        Integer ticketsLeft,         // Matched with "Tickets left"
        List<String> ticketTypes,    // Matched with "Ticket types (standard, vip, ...)"
        List<String> photos
) {
}