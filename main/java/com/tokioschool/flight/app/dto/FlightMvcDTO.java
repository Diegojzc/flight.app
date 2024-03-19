package com.tokioschool.flight.app.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightMvcDTO {

    private Long id;
    private String number;
    private String departure;

    private String arrival;
    private LocalDateTime dayTime;

    private String status;
    private Integer capacity;

}
