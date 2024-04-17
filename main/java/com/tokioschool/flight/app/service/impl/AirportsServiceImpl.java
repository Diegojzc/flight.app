package com.tokioschool.flight.app.service.impl;

import com.tokioschool.flight.app.dto.AirportDTO;
import com.tokioschool.flight.app.repository.AirportRepository;
import com.tokioschool.flight.app.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AirportsServiceImpl  implements AirportService {

    private final AirportRepository airportRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<AirportDTO> getAirports() {

        return airportRepository.findAll()
                .stream()
                .map(airport -> modelMapper.map(airport, AirportDTO.class))
                .toList();
    }
}
