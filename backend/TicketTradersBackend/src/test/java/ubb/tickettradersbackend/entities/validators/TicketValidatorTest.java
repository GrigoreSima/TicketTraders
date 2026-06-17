package ubb.tickettradersbackend.entities.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ubb.tickettradersbackend.entities.Ticket;
import ubb.tickettradersbackend.entities.validators.exceptions.ValidationException;
import ubb.tickettradersbackend.repositories.TicketRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TicketValidatorTest {

    private TicketRepository ticketRepository;
    private TicketValidator ticketValidator;

    @BeforeEach
    void setUp() {
        ticketRepository = Mockito.mock(TicketRepository.class);
        ticketValidator = new TicketValidator(ticketRepository);
    }

    @Test
    void validateForAll_ValidTicket_ShouldNotThrowException() {
        Ticket ticket = new Ticket();
        ticket.setPrice(100.0);
        ticket.setType("VIP");

        assertDoesNotThrow(() -> ticketValidator.validateForAll(ticket));
    }

    @Test
    void validateForAll_NullPrice_ShouldThrowValidationException() {
        Ticket ticket = new Ticket();
        ticket.setPrice(null);
        ticket.setType("VIP");

        ValidationException exception = assertThrows(ValidationException.class, () ->
                ticketValidator.validateForAll(ticket)
        );
        assertEquals("Ticket price cannot be null!", exception.getMessage());
    }

    @Test
    void validateForAll_NegativePrice_ShouldThrowValidationException() {
        Ticket ticket = new Ticket();
        ticket.setPrice(-50.0);
        ticket.setType("VIP");

        ValidationException exception = assertThrows(ValidationException.class, () ->
                ticketValidator.validateForAll(ticket)
        );
        assertEquals("Ticket price cannot be negative!", exception.getMessage());
    }

    @Test
    void validateForAll_NullType_ShouldThrowValidationException() {
        Ticket ticket = new Ticket();
        ticket.setPrice(100.0);
        ticket.setType(null);

        ValidationException exception = assertThrows(ValidationException.class, () ->
                ticketValidator.validateForAll(ticket)
        );
        assertEquals("Ticket type cannot be null or empty!", exception.getMessage());
    }

    @Test
    void validateForSave_ExistingId_ShouldThrowValidationException() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setPrice(100.0);
        ticket.setType("Standard");

        when(ticketRepository.existsById(1L)).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () ->
                ticketValidator.validateForSave(ticket)
        );
        assertEquals("Ticket with this ID already exists!", exception.getMessage());
    }
}