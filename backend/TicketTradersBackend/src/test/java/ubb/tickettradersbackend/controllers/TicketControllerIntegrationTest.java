package ubb.tickettradersbackend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import ubb.tickettradersbackend.entities.dtos.TicketDTO;
import ubb.tickettradersbackend.entities.validators.exceptions.ValidationException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TicketControllerIntegrationTest {

    @Autowired
    private TicketController ticketController;

    @Test
    void testFullTicketLifecycle_IntegrationScenario() {
        Long testId = 999L;

        // 1. Create a valid TicketDTO payload
        TicketDTO newTicket = new TicketDTO(
                testId,
                150.0,
                "VIP",
                LocalDateTime.now().plusDays(5),
                "dummy-qr-code-data",
                null,
                null
        );

        // 2. Test POST /tickets (Add Ticket)
        ResponseEntity<TicketDTO> response = ticketController.addTicket(newTicket);
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        TicketDTO savedTicket = response.getBody();
        assertNotNull(savedTicket);
        assertEquals(150.0, savedTicket.price());
        assertEquals("VIP", savedTicket.type());

        // 3. Test GET /tickets/{id} (Find Ticket)
        ResponseEntity<TicketDTO> fetchResponse = ticketController.getTicket(testId);
        assertEquals(200, fetchResponse.getStatusCode().value());
        assertNotNull(fetchResponse.getBody());
        assertEquals("dummy-qr-code-data", fetchResponse.getBody().qr());

        // 4. Test PATCH /tickets/{id} (Update Ticket)
        TicketDTO updatePayload = new TicketDTO(
                testId,
                180.0,
                "VIP Platinum",
                savedTicket.buyTime(),
                savedTicket.qr(),
                savedTicket.eventId(),
                savedTicket.userId()
        );

        ResponseEntity<?> updateResponse = ticketController.updateTicket(testId, updatePayload);
        assertEquals(200, updateResponse.getStatusCode().value());

        // 5. Test DELETE /tickets/{id} (Remove Ticket)
        ResponseEntity<?> deleteResponse = ticketController.deleteTicket(testId);
        assertEquals(200, deleteResponse.getStatusCode().value());
    }

    @Test
    void testAddTicket_ValidationFailure_ShouldThrowValidationException() {
        // Create an invalid payload
        TicketDTO invalidTicket = new TicketDTO(
                888L,
                -25.0, // Invalid negative price!
                "Standard",
                LocalDateTime.now(),
                "qr-data",
                null,
                null
        );

        assertThrows(ValidationException.class, () -> {
            ticketController.addTicket(invalidTicket);
        });
    }
}