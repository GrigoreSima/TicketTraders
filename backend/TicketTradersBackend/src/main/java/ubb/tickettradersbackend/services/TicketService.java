package ubb.tickettradersbackend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ubb.tickettradersbackend.entities.Ticket;
import ubb.tickettradersbackend.entities.dtos.TicketDTO;
import ubb.tickettradersbackend.entities.validators.TicketValidator;
import ubb.tickettradersbackend.repositories.TicketRepository;

@Service
public class TicketService {

    private static final Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final ConversionService conversionService;
    private final TicketValidator ticketValidator;

    public TicketService(TicketRepository ticketRepository, ConversionService conversionService, TicketValidator ticketValidator) {
        this.ticketRepository = ticketRepository;
        this.conversionService = conversionService;
        this.ticketValidator = ticketValidator;
    }

    public TicketDTO addTicket(TicketDTO ticketDTO) {
        log.info("Adding ticketDTO {}", ticketDTO);

        Ticket ticket = conversionService.convert(ticketDTO, Ticket.class);

        if (ticket == null) {
            return null;
        }

        ticketValidator.validateForSave(ticket);
        return conversionService.convert(ticketRepository.save(ticket), TicketDTO.class);
    }

    public void updateTicket(Long ticketID, TicketDTO ticketDTO) {
        log.info("Updating ticket {}", ticketID);

        Ticket ticket = conversionService.convert(ticketDTO, Ticket.class);

        if (ticket == null) {
            return;
        }

        ticketValidator.validateForModify(ticket);
        ticketRepository.save(ticket);
    }

    public void removeTicket(Long ticketID) {
        log.info("Removing ticket {}", ticketID);
        ticketRepository.deleteById(ticketID);
    }

    public TicketDTO findTicket(Long ticketID) {
        log.info("Finding ticket {}", ticketID);
        Ticket ticket = ticketRepository.findById(ticketID).orElseThrow();
        return conversionService.convert(ticket, TicketDTO.class);
    }
}