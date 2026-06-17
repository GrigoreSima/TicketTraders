package ubb.tickettradersbackend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.tickettradersbackend.entities.dtos.TicketDTO;
import ubb.tickettradersbackend.services.TicketService;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private static final Logger log = LoggerFactory.getLogger(TicketController.class);
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketDTO> addTicket(@RequestBody TicketDTO ticketDTO) {
        log.info("Adding ticket {}", ticketDTO);
        return ResponseEntity.ok(ticketService.addTicket(ticketDTO));
    }

    @PatchMapping("/{ticketID}")
    public ResponseEntity<?> updateTicket(@PathVariable Long ticketID, @RequestBody TicketDTO ticketDTO) {
        log.info("Updating ticket {}", ticketID);
        ticketService.updateTicket(ticketID, ticketDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ticketID}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long ticketID) {
        log.info("Deleting ticket {}", ticketID);
        ticketService.removeTicket(ticketID);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{ticketID}")
    public ResponseEntity<TicketDTO> getTicket(@PathVariable Long ticketID) {
        log.info("Getting ticket {}", ticketID);
        return ResponseEntity.ok(ticketService.findTicket(ticketID));
    }
}