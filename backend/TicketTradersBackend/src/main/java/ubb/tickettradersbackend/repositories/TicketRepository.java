package ubb.tickettradersbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ubb.tickettradersbackend.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}