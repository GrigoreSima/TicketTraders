package ubb.tickettradersbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tt_ticket")
public class Ticket extends Base {
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Represents the buyer

    private LocalDateTime buyTime; // Tracks the purchase timestamp

    @Column(nullable = false)
    private Double price;

    @Lob
    private String qr; // Holds the QR code data or image reference

    @Column(nullable = false)
    private String type; // e.g., "VIP", "REGULAR"

}