package com.tokioschool.flight.app.service;

public interface FlightEmailService {

    void sendFlightEmail(Long flightId, String to);
}
