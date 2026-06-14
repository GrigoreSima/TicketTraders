package ubb.tickettradersbackend.entities.validators;

import org.springframework.stereotype.Component;
import ubb.tickettradersbackend.entities.Event;
import ubb.tickettradersbackend.entities.validators.exceptions.ValidationException;
import ubb.tickettradersbackend.repositories.EventRepository;

@Component
public class EventValidator implements Validator<Event> {
    private final EventRepository eventRepository;

    public EventValidator(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void validateForAll(Event obj) throws ValidationException {
        validateName(obj.getName());
        validateCapacity(obj.getCapacity());
    }

    @Override
    public void validateForModify(Event obj) throws ValidationException {
        validateForAll(obj);
    }

    @Override
    public void validateForSave(Event obj) throws ValidationException {
        validateForAll(obj);
        validateID(obj.getId());
    }

    private void validateID(Long id) throws ValidationException {
        if (id != null && eventRepository.existsById(id)) {
            throw new ValidationException("Event with this ID already exists!");
        }
    }

    private void validateName(String name) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Event name cannot be null or empty!");
        }
    }

    private void validateCapacity(Integer capacity) throws ValidationException {
        if (capacity != null && capacity < 0) {
            throw new ValidationException("Event capacity cannot be negative!");
        }
    }
}