package com.tokioschool.flight.app.repository;

import com.tokioschool.flight.app.domain.Flight;
import com.tokioschool.flight.app.domain.FlightStatus;
import com.tokioschool.flight.app.projection.FlightCounterByAirport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightDAO extends JpaRepository<Flight, Long> {

    List<Flight> findByDepartureAcronym(String departureAcronym);

    List<Flight> findByDepartureTimeIsAfterAndStatusIs(LocalDateTime departureTimeFrom, FlightStatus flightStatus);


    @Query("""

    SELECT f.departure.acronym AS acronym, COUNT(1) AS counter FROM Flight f GROUP BY f.departure.acronym
    """)
    List<FlightCounterByAirport> getFlightCounterByDepartureAirport();

    @Query(value = """
    SELECT airports.id AS acronym, COUNT(1) AS COUNTER FROM flights JOIN airports ON flights.airport_arrival_id = airport.id
    GROUP BY airports.id
    """, nativeQuery = true)
    List<FlightCounterByAirport> getFlightCounterByArrivalAirport();
}
