package com.tokioschool.flight.app.dto;


import lombok.Builder;
import lombok.Value;

@Value
@Builder

public class FlightImageResourceDTO {
    String contentType;
    int size;
    String filename;
    byte[] content;
}
