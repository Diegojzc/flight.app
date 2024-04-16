package com.tokioschool.flight.converter;

import com.tokioschool.flight.app.core.converter.ModelMapperConfiguration;
import com.tokioschool.flight.app.domain.Airport;
import com.tokioschool.flight.app.domain.Flight;
import com.tokioschool.flight.app.domain.FlightImage;
import com.tokioschool.flight.app.domain.FlightStatus;
import com.tokioschool.flight.app.dto.FlightDTO;
import com.tokioschool.flight.app.dto.ResourceDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.UUID;

import static net.bytebuddy.matcher.ElementMatchers.returns;
import static org.assertj.core.api.Assertions.assertThat;

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
                .image(FlightImage.builder()
                        .contentType("contentType")
                        .resourceId(UUID.randomUUID())
                        .filename("filename")
                        .size(10)
                        .build())
                .build();

        FlightDTO flightDTO = modelMapper.map(flight, FlightDTO.class);
                assertThat(flightDTO)
                        .returns(flight.getId(), FlightDTO::getId)
                        .returns (flight. getCapacity(), FlightDTO::getCapacity)
                        .returns (flight.getNumber(), FlightDTO::getNumber)
                        .returns (flight.getDeparture().getAcronym(), FlightDTO::getDepartureAcronym)
                        .returns (flight.getArrival().getAcronym(), FlightDTO:: getArrivalAcronym)
                        .returns (flight.getDepartureTime(), FlightDTO::getDepartureTime)
                        .returns (flight.getStatus().name(), o -> o.getStatus().name())
                        . satisfies(o -> assertThat (o.getImage()).isNotNull());
        ResourceDTO resourceDTO = flightDTO.getImage ();
        assertThat (resourceDTO)
                .returns ( "contentType", ResourceDTO:: getContentType)
                .returns (  "filename", ResourceDTO:: getFilename)
                .returns (10, ResourceDTO:: getSize)
                .satisfies(o -> assertThat (o.getResourceId()).isNotNull ()) ;

    }
}
