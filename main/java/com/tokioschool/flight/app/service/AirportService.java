package com.tokioschool.flight.app.service;

import com.tokioschool.flight.app.dto.AirportDTO;

import java.util.List;

public interface AirportService {

    List<AirportDTO> getAirports();
}
