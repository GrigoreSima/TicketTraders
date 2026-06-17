package ubb.tickettradersbackend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import ubb.tickettradersbackend.entities.dtos.EventDTO;
import ubb.tickettradersbackend.entities.validators.exceptions.ValidationException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class EventControllerIntegrationTest {

    @Autowired
    private EventController eventController;

    @Test
    void testFullEventLifecycle_IntegrationScenario() {
        Long testId = 999L;

        // 1. Create a valid EventDTO payload with an assigned ID
        EventDTO newEvent = new EventDTO(
                testId,
                "Untold",
                "Testing end-to-end flow",
                null,
                "Cluj Arena",
                "46.7689,23.5724",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3),
                List.of("music", "festival"),
                0,
                List.of(),
                List.of()
        );

        ResponseEntity<EventDTO> response = eventController.addEvent(newEvent);
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        EventDTO savedEvent = response.getBody();
        assertNotNull(savedEvent);
        assertEquals("Untold", savedEvent.name());

        // 2. Test GET /events/{id} (Find Event)
        ResponseEntity<EventDTO> fetchResponse = eventController.getEvent(testId);
        assertEquals(200, fetchResponse.getStatusCode().value());
        assertNotNull(fetchResponse.getBody());
        assertEquals("Cluj Arena", fetchResponse.getBody().address());

        // 3. Test PATCH /events/{id} (Update Event)
        EventDTO updatePayload = new EventDTO(
                testId,
                "Updated Untold Title",
                savedEvent.description(),
                savedEvent.organizerId(),
                "New Address",
                savedEvent.coordinates(),
                savedEvent.startDateTime(),
                savedEvent.endDateTime(),
                savedEvent.tags(),
                savedEvent.ticketsLeft(),
                savedEvent.ticketTypes(),
                savedEvent.photos()
        );

        ResponseEntity<?> updateResponse = eventController.updateEvent(testId, updatePayload);
        assertEquals(200, updateResponse.getStatusCode().value());

        // 4. Test DELETE /events/{id} (Remove Event)
        ResponseEntity<?> deleteResponse = eventController.deleteEvent(testId);
        assertEquals(200, deleteResponse.getStatusCode().value());
    }

    @Test
    void testAddEvent_ValidationFailure_ShouldThrowValidationException() {
        EventDTO invalidEvent = new EventDTO(
                888L,
                "", // Invalid empty name
                "Should fail",
                null,
                "Somewhere",
                "0,0",
                LocalDateTime.now(),
                LocalDateTime.now(),
                List.of(),
                -100,
                List.of(),
                List.of()
        );

        assertThrows(ValidationException.class, () -> {
            eventController.addEvent(invalidEvent);
        });
    }
}