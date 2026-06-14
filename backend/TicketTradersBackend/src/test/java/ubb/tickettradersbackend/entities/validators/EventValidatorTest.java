package ubb.tickettradersbackend.entities.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ubb.tickettradersbackend.entities.Event;
import ubb.tickettradersbackend.entities.validators.exceptions.ValidationException;
import ubb.tickettradersbackend.repositories.EventRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class EventValidatorTest {

    private EventRepository eventRepository;
    private EventValidator eventValidator;

    @BeforeEach
    void setUp() {
        eventRepository = Mockito.mock(EventRepository.class);
        eventValidator = new EventValidator(eventRepository);
    }

    @Test
    void validateForAll_ValidEvent_ShouldNotThrowException() {
        Event event = new Event();
        event.setName("Untold Festival");
        event.setCapacity(50000);

        assertDoesNotThrow(() -> eventValidator.validateForAll(event));
    }

    @Test
    void validateForAll_NullName_ShouldThrowValidationException() {
        Event event = new Event();
        event.setName(null);
        event.setCapacity(50000);

        ValidationException exception = assertThrows(ValidationException.class, () ->
                eventValidator.validateForAll(event)
        );
        assertEquals("Event name cannot be null or empty!", exception.getMessage());
    }

    @Test
    void validateForAll_NegativeCapacity_ShouldThrowValidationException() {
        Event event = new Event();
        event.setName("Electric Castle");
        event.setCapacity(-10);

        ValidationException exception = assertThrows(ValidationException.class, () ->
                eventValidator.validateForAll(event)
        );
        assertEquals("Event capacity cannot be negative!", exception.getMessage());
    }

    @Test
    void validateForSave_ExistingId_ShouldThrowValidationException() {
        Event event = new Event();
        event.setId(99L);
        event.setName("Summer Well");
        event.setCapacity(10000);

        when(eventRepository.existsById(99L)).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () ->
                eventValidator.validateForSave(event)
        );
        assertEquals("Event with this ID already exists!", exception.getMessage());
    }
}