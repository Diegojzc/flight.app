package com.tokioschool.flight.app.service.impl;

import com.tokioschool.flight.app.domain.Airport;
import com.tokioschool.flight.app.domain.Flight;
import com.tokioschool.flight.app.domain.FlightImage;
import com.tokioschool.flight.app.domain.FlightStatus;
import com.tokioschool.flight.app.dto.FlightMvcDTO;
import com.tokioschool.flight.app.dto.FlightDTO;
import com.tokioschool.flight.app.repository.AirportRepository;
import com.tokioschool.flight.app.repository.FlightRepository;
import com.tokioschool.flight.app.service.FlightImageService;
import com.tokioschool.flight.app.service.FlightService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor

public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;
    private final AirportRepository airportRepository;
    private final FlightImageService flightImageService;


    @Override
    public List<FlightDTO> getFlights() {
     return flightRepository.findAll()
                .stream()
                .map(flight -> modelMapper.map(flight, FlightDTO.class))
                .toList();

    }

    @Override
    public FlightDTO getFlight(Long flightId) {
    return flightRepository
        .findById(flightId)
        .map(flight -> modelMapper.map(flight, FlightDTO.class))
        .orElseThrow(() -> new IllegalArgumentException("Flight with id:%s not found".formatted(flightId)));
    }

    @Override
    @Transactional
    public FlightDTO createFlight(FlightMvcDTO flightMvcDTO, @Nullable MultipartFile multipartFile) {

        Flight flight = createdOrEdit(new Flight(), flightMvcDTO,multipartFile);
        return modelMapper.map(flight,FlightDTO.class);
    }

    @Override
    @Transactional
    public FlightDTO editFlight(FlightMvcDTO flightMvcDTO, @Nullable MultipartFile multipartFile) {
         Flight flight = flightRepository
                 .findById(flightMvcDTO.getId())
                 .orElseThrow(() -> new IllegalArgumentException("Flight with id:%s not found".formatted(flightMvcDTO.getId())));
        flight = createdOrEdit(flight, flightMvcDTO,multipartFile);
        return modelMapper.map(flight,FlightDTO.class);
    }

    protected Flight createdOrEdit(Flight flight, FlightMvcDTO flightMvcDTO, MultipartFile multipartFile) {

        Airport departure = getAirport(flightMvcDTO.getDeparture());
        Airport arrival = getAirport(flightMvcDTO.getArrival());

         FlightImage flightImage =flight.getImage();

        if (!multipartFile.isEmpty()){
            flightImage= flightImageService.saveImage(multipartFile);
            flightImage.setFlight(flight);
        }

        flight.setCapacity(flightMvcDTO.getCapacity());
        flight.setArrival(arrival);
        flight.setDeparture(departure);
        flight.setStatus(FlightStatus.valueOf(flightMvcDTO.getStatus()));
        flight.setNumber(flightMvcDTO.getNumber());
        flight.setDepartureTime(flightMvcDTO.getDayTime());
        flight.setImage(flightImage);
        return flightRepository.save(flight);

    }

    protected Airport getAirport(String acronym){
       return  airportRepository.findByAcronym(acronym)
               .orElseThrow(()->new IllegalArgumentException("Airport with id:%s not found".formatted(acronym)));
    }
}
