package ubb.tickettradersbackend.entities.validators;

import org.springframework.stereotype.Component;
import ubb.tickettradersbackend.entities.Ticket;
import ubb.tickettradersbackend.entities.validators.exceptions.ValidationException;
import ubb.tickettradersbackend.repositories.TicketRepository;

@Component
public class TicketValidator implements Validator<Ticket> {
    private final TicketRepository ticketRepository;

    public TicketValidator(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void validateForAll(Ticket obj) throws ValidationException {
        validatePrice(obj.getPrice());
        validateType(obj.getType());
    }

    @Override
    public void validateForModify(Ticket obj) throws ValidationException {
        validateForAll(obj);
    }

    @Override
    public void validateForSave(Ticket obj) throws ValidationException {
        validateForAll(obj);
        validateID(obj.getId());
    }

    private void validateID(Long id) throws ValidationException {
        if (id != null && ticketRepository.existsById(id)) {
            throw new ValidationException("Ticket with this ID already exists!");
        }
    }

    private void validatePrice(Double price) throws ValidationException {
        if (price == null) {
            throw new ValidationException("Ticket price cannot be null!");
        }
        if (price < 0) {
            throw new ValidationException("Ticket price cannot be negative!");
        }
    }

    private void validateType(String type) throws ValidationException {
        if (type == null || type.trim().isEmpty()) {
            throw new ValidationException("Ticket type cannot be null or empty!");
        }
    }
}