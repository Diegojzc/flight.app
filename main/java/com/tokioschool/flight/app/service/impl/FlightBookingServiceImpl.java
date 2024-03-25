package com.tokioschool.flight.app.service.impl;

import com.tokioschool.flight.app.domain.Flight;
import com.tokioschool.flight.app.domain.FlightBooking;
import com.tokioschool.flight.app.domain.User;
import com.tokioschool.flight.app.dto.FlightBookingDTO;
import com.tokioschool.flight.app.dto.FlightDTO;
import com.tokioschool.flight.app.repository.FlightRepository;
import com.tokioschool.flight.app.repository.UserRepository;
import com.tokioschool.flight.app.service.FlightBookingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightBookingServiceImpl implements FlightBookingService {

    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapoer;
    @Override
    @Transactional
    public FlightBookingDTO bookFlight(Long flightId, String userId) {

       Flight flight = flightRepository.findById(flightId)
                .orElseThrow(()-> new IllegalArgumentException("Flight with id:%s not fount".formatted(flightId)));

       User user = userRepository.findById(userId)
               .orElseThrow(()-> new IllegalArgumentException("User with id:%s not fount".formatted(userId)));

       if(flight.getOccupancy() >= flight.getCapacity()){
           throw  new IllegalStateException("Flight with id:%s without places".formatted(flightId));
       }

        FlightBooking flightBooking = FlightBooking.builder()
                .user(user)
                .flight(flight)
                .locator(UUID.randomUUID())
                .build();
       flight.setOccupancy(flight.getOccupancy() +1);
       flight.getBookings().add(flightBooking);

       flightRepository.save(flight);

        return modelMapoer.map(flightBooking, FlightBookingDTO.class);

    }
}
