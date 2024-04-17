package com.tokioschool.flight.app.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirportDTO{

    private String acronym;
    private String name;
    private String country;
}
