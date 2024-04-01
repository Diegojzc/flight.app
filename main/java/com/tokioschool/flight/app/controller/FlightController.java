package com.tokioschool.flight.app.controller;

import com.tokioschool.flight.app.dto.AirportDTO;
import com.tokioschool.flight.app.dto.FlightMvcDTO;
import com.tokioschool.flight.app.dto.FlightDTO;
import com.tokioschool.flight.app.dto.ResourceDTO;
import com.tokioschool.flight.app.service.AirportService;
import com.tokioschool.flight.app.service.FlightService;
import com.tokioschool.flight.app.validator.FlightMvcDTOValidator;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FlightController {

  private final FlightService flightService;
  private final AirportService airportService;
  private final FlightMvcDTOValidator flightMvcDTOValidator;
  @InitBinder
  private void initBinder(WebDataBinder webDataBinder){
    webDataBinder.setValidator(flightMvcDTOValidator);
  }

  @GetMapping("/flight/flights")
  public ModelAndView getFlights() {

    List<FlightDTO> flights = flightService.getFlights();

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("/flight/flights/flights-list");
    modelAndView.addObject("flights", flights);
    return modelAndView;
  }

  @GetMapping({"/flight/flights-edit", "/flight/flights-edit/{flightId}"})
  public ModelAndView createOrEditFlight(
      @PathVariable(name = "flightId", required = false) Long flightId, Model model) {

    Optional<FlightDTO> maybeFlightDTO =
        Optional.ofNullable(flightId).map(flightService::getFlight);

    FlightMvcDTO flIghtMvcDTO = maybeFlightDTO
            .map(
                flightDTO ->
                    FlightMvcDTO.builder()
                        .id(flightDTO.getId())
                        .number(flightDTO.getNumber())
                        .capacity(flightDTO.getCapacity())
                        .arrival(flightDTO.getArrivalAcronym())
                        .departure(flightDTO.getDepartureAcronym())
                        .status(flightDTO.getStatus().name())
                        .dayTime(flightDTO.getDepartureTime())
                        .build())
            .orElseGet(FlightMvcDTO::new);
    ModelAndView modelAndView =
        populateCreate0rEditFlightModel(flIghtMvcDTO, maybeFlightDTO.orElse(null), model);
    modelAndView.setViewName("/flight/flights/flights-edit");
    return modelAndView;
  }



  @PostMapping({"/flight/flights-edit", "/flight/flights-edit/", "/flight/flights-edit/{flightId}"})
  public ModelAndView createdOrEditFlightPost(
          @ModelAttribute("flight") @Valid FlightMvcDTO flightMvcDTO,
          BindingResult bindingResult,
          @RequestParam("image") MultipartFile multipartFile,
          @PathVariable(name = "flightId", required = false) Long flightId,
          Model model, RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      Optional<FlightDTO> maybeFlightDTO = Optional.ofNullable(flightId).map(flightService::getFlight);

      ModelAndView modelAndView = populateCreate0rEditFlightModel(flightMvcDTO, maybeFlightDTO.orElse(null), model);
      modelAndView.setViewName("flight/flights/flights-edit");

      return modelAndView;
    }

    Optional.ofNullable(flightMvcDTO.getId())
        .map(o -> flightService.editFlight(flightMvcDTO, multipartFile))
        .orElseGet(() -> flightService.createFlight(flightMvcDTO, multipartFile));

    return new ModelAndView("redirect:/flight/flights");
  }



  private ModelAndView populateCreate0rEditFlightModel(
      FlightMvcDTO flightMvcDTO, @Nullable FlightDTO flightDTO, Model model) {

    List<AirportDTO> airports = airportService.getAirports();
    UUID imageId =
        Optional.ofNullable(flightDTO)
            .map(FlightDTO::getImage)
            .map(ResourceDTO::getResourceId)
            .orElse(null);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addAllObjects(model.asMap());
    modelAndView.addObject("flight", flightMvcDTO);
    modelAndView.addObject("airports", airports);
    modelAndView.addObject("flightImageResourceId", imageId);
    return modelAndView;
  }
}
