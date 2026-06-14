package ubb.tickettradersbackend.entities.converters;

import org.springframework.core.convert.converter.Converter;
import ubb.tickettradersbackend.entities.Ticket;
import ubb.tickettradersbackend.entities.dtos.TicketDTO;

public class TicketDTOConverter implements Converter<TicketDTO, Ticket> {

    @Override
    public Ticket convert(TicketDTO source) {
        // Create a new empty entity and map the simple fields from the DTO
        Ticket ticket = new Ticket();
        ticket.setPrice(source.price());
        ticket.setType(source.type());
        ticket.setBuyTime(source.buyTime());
        ticket.setQr(source.qr());
        return ticket;
    }
}