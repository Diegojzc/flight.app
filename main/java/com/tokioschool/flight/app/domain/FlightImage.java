package com.tokioschool.flight.app.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flight_images")
public class FlightImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID resourceId;

    private String filename;
    private String contentType;
    private int size;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "flight_id")
    private Flight flight;


}
