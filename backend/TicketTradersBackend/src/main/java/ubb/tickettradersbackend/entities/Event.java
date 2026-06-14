package ubb.tickettradersbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tt_event")
public class Event extends Base {

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer; // The user who hosts the event

    private String address;
    private String coordinates; // e.g., "46.7712,23.6236"

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ElementCollection
    @CollectionTable(name = "tt_event_tags", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "tag")
    private List<String> tags; // List of tags for filtering events

    private Integer capacity;

    @ManyToMany
    @JoinTable(
            name = "tt_event_attendees",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> attendees; // List of users attending the event

    private boolean isSponsored;
    private Double score; // Rating score

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Ticket> tickets; // Available tickets for this event

    @ElementCollection
    @CollectionTable(name = "tt_event_photos", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "photo_url")
    private List<String> photos; // Photo URLs for the event gallery
}