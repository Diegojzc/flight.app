package com.tokioschool.flight.app.service;

import com.tokioschool.flight.app.dto.FlightMvcDTO;
import com.tokioschool.flight.app.dto.FlightDTO;
import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FlightService {

    List<FlightDTO> getFlights();

    FlightDTO getFlight(Long flightId);

    FlightDTO createFlight(FlightMvcDTO flIghtMvcDTO, @Nullable MultipartFile multipartFile);

    FlightDTO editFlight(FlightMvcDTO flIghtMvcDTO, @Nullable MultipartFile multipartFile);
}
