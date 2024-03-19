package com.tokioschool.flight.app.Controller;

import com.tokioschool.flight.app.dto.AirportDTO;
import com.tokioschool.flight.app.dto.FlightMvcDTO;
import com.tokioschool.flight.app.dto.FlightDTO;
import com.tokioschool.flight.app.service.AirportService;
import com.tokioschool.flight.app.service.FlightService;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;
    private final AirportService airportService;

    @GetMapping("/flight/flights")
    public ModelAndView getFlights(){

        List<FlightDTO> flights = flightService.getFlights();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/flight/flights/flights-list");
        modelAndView.addObject("flights", flights);
        return modelAndView;

    }
    @GetMapping({"/flight/Flights-edit", "/flight/flights-edit/{flightId}"})
    public ModelAndView createOrEditFlight(@PathVariable(name = "flightId", required = false) Long flightId, Model model){

        Optional<FlightDTO> maybeFlightDTO = Optional.ofNullable(flightId).map(flightService::getFlight);

        FlightMvcDTO flIghtMvcDTO = maybeFlightDTO
                .map(flightDTO -> FlightMvcDTO.builder()
                        .id(flightDTO.getId())
                                .number(flightDTO.getNumber())
                                .capacity(flightDTO.getCapacity())
                                .arrival(flightDTO.getArrivalAcronym())
                                .departure (flightDTO.getDepartureAcronym())
                                .status (flightDTO.getStatus().name ())
                        .dayTime(flightDTO.getDepartureTime())
                                  .build ())
                .orElseGet(FlightMvcDTO::new);
        ModelAndView modelAndView =
        populateCreate0rEditFlightModel(flIghtMvcDTO,maybeFlightDTO.orElse(null),model);
        modelAndView.setViewName("/flight/flights/flight-edit");
        return modelAndView;

    }
    private ModelAndView populateCreate0rEditFlightModel( FlightMvcDTO flightMvcDTO, @Nullable FlightDTO flightDTO, Model model) {

        List<AirportDTO> airports = airportService.getAirports();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addAllObjects(model.asMap());
        modelAndView.addObject (  "flight", flightMvcDTO);
        modelAndView.addObject ( "airports", airports);
        return modelAndView;
}
}
