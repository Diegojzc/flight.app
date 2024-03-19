package com.tokioschool.flight.converter;

import com.tokioschool.flight.app.core.converter.ModelMapperConfiguration;
import com.tokioschool.flight.app.domain.Airport;
import com.tokioschool.flight.app.domain.Flight;
import com.tokioschool.flight.app.domain.FlightStatus;
import com.tokioschool.flight.app.dto.FlightDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

public class FlightConverterTest {

    private final ModelMapper modelMapper= new ModelMapperConfiguration().modelMapper();

    @Test
    void givenUser_whenConvertedToDTO_thenOk(){
        Flight flight= Flight.builder()
                .id(1L)
                .created(LocalDateTime.now())
                .number("number")
                .capacity(10)
                .departure(Airport.builder().acronym("BCN").build())
                .arrival(Airport.builder().acronym("IBZ").build())
                .departureTime(LocalDateTime.of(2002,11,2,12,30,0))
                .status(FlightStatus.SCHEDULED)
                .occupancy(2)
                .image(null)
                .build();

        FlightDTO flightDTO = modelMapper.map(flight, FlightDTO.class);
                Assertions.assertThat(flightDTO)
                        .returns(flight.getId(), FlightDTO::getId)
                        .returns (flight. getCapacity(), FlightDTO::getCapacity)
                        .returns (flight.getNumber(), FlightDTO::getNumber)
                        .returns (flight.getDeparture().getAcronym(), FlightDTO::getDepartureAcronym)
                        .returns (flight.getArrival().getAcronym(), FlightDTO:: getArrivalAcronym)
                        .returns (flight.getDepartureTime(), FlightDTO::getDepartureTime)
                        .returns (flight.getStatus().name(), o -> o.getStatus().name())
                        . satisfies(o -> Assertions.assertThat (o.getImage()).isNull());

    }
}
