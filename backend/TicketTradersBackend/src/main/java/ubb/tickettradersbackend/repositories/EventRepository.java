package ubb.tickettradersbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ubb.tickettradersbackend.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}