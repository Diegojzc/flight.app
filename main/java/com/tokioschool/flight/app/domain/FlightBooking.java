package com.tokioschool.flight.app.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "flight_with_users")
public class FlightBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime created;

    @Column
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID locator;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name= "flight_id")
    private Flight flight;




}
