package com.tokioschool.flight.app.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "airports")

public class Airport {
    @Id
    @Column(name= "id")
    private String acronym;
    private String name;
    private String country;
    private BigDecimal latitud;
    private BigDecimal longitud;
}
