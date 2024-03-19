package com.tokioschool.flight.app.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {

    private Long id;
    private String number;
    private String departureAcronym;
    private String arrivalAcronym;
    private LocalDateTime departureTime;
    private Status status;
    private Integer capacity;
    private Integer occupancy;
    private String image;

    public enum Status{
        SCHEDULED,
        CANCELLED;
    }
}
