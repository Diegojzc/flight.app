package com.tokioschool.flight.app.repository;

import com.tokioschool.flight.app.domain.Airport;
import com.tokioschool.flight.app.domain.Flight;
import com.tokioschool.flight.app.domain.FlightImage;
import com.tokioschool.flight.app.domain.FlightStatus;
import com.tokioschool.flight.app.projection.FlightCounterByAirport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.assertj.core.api.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static net.bytebuddy.matcher.ElementMatchers.returns;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest(
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MYSQL;DATABASE_TO_LOWER=TRUE;",
                "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
                "spring.jpa.hibernate.ddl-auto:create-drop"
        })
class FlightDAOTest {

    @Autowired
    private FlightImageDAO flightImageDAO;
    @Autowired
    private FlightDAO flightDAO;
    @Autowired
    private AirportDAO airportDAO;

    @BeforeEach
void beforeEach(){

    Airport bcn= Airport.builder().acronym("BCN")
                .name("Barcleona airport")
                .country("Spain")
                .build();

    Airport gla = Airport.builder().acronym("GLA")
                .name("Glasgow airport")
                .country("United Kingdom")
                .build();

    airportDAO.save(bcn);
    airportDAO.save(gla);

    Flight flight1 = Flight.builder().occupancy(0)
                .capacity(90)
                .departure(bcn)
                .arrival(gla)
                .number("EJU3037")
                .departureTime(LocalDateTime.now().plusSeconds(60))
                .status(FlightStatus.SCHEDULED)
                .bookings(new HashSet<>())
                .build();

    FlightImage flightImage = FlightImage.builder()
            .flight(flight1)
            .resourceId(UUID.randomUUID())
            .contentType("image/jpeg")
            .size(20)
            .build();

    flight1.setImage(flightImage);


    Flight flight2= Flight.builder().occupancy(0)
            .capacity(90)
            .departure(bcn)
            .arrival(gla)
            .number("KLM3037")
            .departureTime(LocalDateTime.now().plusSeconds(60))
            .status(FlightStatus.CANCELLED)
            .bookings(new HashSet<>())
            .build();

        Flight flight3= Flight.builder().occupancy(0)
                .capacity(90)
                .departure(bcn)
                .arrival(gla)
                .number("EJU3037")
                .departureTime(LocalDateTime.now().plusSeconds(60))
                .status(FlightStatus.SCHEDULED)
                .bookings(new HashSet<>())
                .build();

        flightDAO.saveAll(List.of(flight1,flight2,flight3));


}

@Test
    void givenFlights_whenFindByDeparture_thenOk(){

        List<Flight> flightBcn =flightDAO.findByDepartureAcronym("BCN");
    Assertions.assertEquals(3, flightBcn.size());

    List<Flight> flightGla = flightDAO.findByDepartureAcronym("GLA");
    Assertions.assertEquals(0, flightGla.size());


}

@Test
      void givenFlight_whenFindNextFlights_thenOk(){
        List<Flight> flights= flightDAO.findByDepartureTimeIsAfterAndStatusIs(LocalDateTime.now(), FlightStatus.SCHEDULED);
        Assertions.assertEquals(2, flights.size());
        Assertions.assertNotNull(flights.get(0).getImage());
}
    @Test
    void givenFlights_whenFindDepartureCounters_then0k(){
        List<FlightCounterByAirport> flightCounterByAirport = flightDAO.getFlightCounterByDepartureAirport();
        assertThat(flightCounterByAirport).hasSize(1);
        assertThat(flightCounterByAirport.get (0))
                .returns( 3L, FlightCounterByAirport::getCounter)
                .returns( "BCN", FlightCounterByAirport::getAcronym);
}
}
