package ubb.tickettradersbackend.entities.dtos;

import java.time.LocalDateTime;

public record TicketDTO(
        Long id,
        Double price,
        String type,
        LocalDateTime buyTime,
        String qr,
        Long eventId,
        Long userId
) {
}