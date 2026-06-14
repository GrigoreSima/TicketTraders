package ubb.tickettradersbackend.entities.converters;

import org.springframework.core.convert.converter.Converter;
import ubb.tickettradersbackend.entities.Ticket;
import ubb.tickettradersbackend.entities.dtos.TicketDTO;

public class TicketConverter implements Converter<Ticket, TicketDTO> {
    @Override
    public TicketDTO convert(Ticket source) {
        return new TicketDTO(
                source.getId(),
                source.getPrice(),
                source.getType(),
                source.getBuyTime(),
                source.getQr(),
                source.getEvent() != null ? source.getEvent().getId() : null,
                source.getUser() != null ? source.getUser().getId() : null
        );
    }
}