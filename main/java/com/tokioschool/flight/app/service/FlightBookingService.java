package com.tokioschool.flight.app.service;

import com.tokioschool.flight.app.dto.FlightBookingDTO;

public interface FlightBookingService {

    FlightBookingDTO bookFlight(Long flightId, String userId);

}
