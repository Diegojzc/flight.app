package com.tokioschool.flight.app.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flights")

public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @CreationTimestamp
    private LocalDateTime created;

    private String number;

    @ManyToOne
    @JoinColumn(name= "airport_departure_id", nullable = false)
    private Airport departure;

    @ManyToOne
    @JoinColumn(name= "airport_arrival_id", nullable = false)
    private Airport arrival;

    @Column(name= "departure_time")
    private LocalDateTime departureTime;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    @Column(nullable = false)
    private int occupancy;

    @Column(nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FlightBooking> bookings;

    @OneToOne(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name= "flight_image_id",referencedColumnName = "id")
    private FlightImage image;


}
