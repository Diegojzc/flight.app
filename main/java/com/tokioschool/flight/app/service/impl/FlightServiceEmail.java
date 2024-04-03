package com.tokioschool.flight.app.service.impl;

import com.tokioschool.flight.app.domain.Flight;
import com.tokioschool.flight.app.dto.FlightImageResourceDTO;
import com.tokioschool.flight.app.email.dto.AttachmentDTO;
import com.tokioschool.flight.app.email.dto.EmailDTO;
import com.tokioschool.flight.app.email.service.EmailService;
import com.tokioschool.flight.app.repository.FlightRepository;
import com.tokioschool.flight.app.service.FlightEmailService;
import com.tokioschool.flight.app.service.FlightImageService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightServiceEmail implements FlightEmailService {

  private final EmailService emailService;
  private final FlightRepository flightRepository;
  private final FlightImageService flightImageService;
  private final MessageSource messageSource;

  @Override
  public void sendFlightEmail(Long flightId, String to) {

    Flight flight =
        flightRepository
            .findById(flightId)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Flight with id:%s not found".formatted(flightId)));

    Optional<FlightImageResourceDTO> maybeFlightImageResourceDTO =
        Optional.ofNullable(flight.getImage())
            .map(flightImage -> flightImageService.getImage(flightImage.getResourceId()));

    List<AttachmentDTO> attachmentDTOS =
        maybeFlightImageResourceDTO
            .map(
                flightImageResourceDTO ->
                    AttachmentDTO.builder()
                        .contentType(flightImageResourceDTO.getContentType())
                        .fileName(flightImageResourceDTO.getFilename())
                        .content(flightImageResourceDTO.getContent())
                        .build())
            .stream()
            .toList();

    Locale localeSpanish = new Locale.Builder().setLanguage("es").build();

    String subjectEn = buildSubject(flight, Locale.ENGLISH);
    String subjectEs = buildSubject(flight, localeSpanish);

    String bodyEn = buildBody(flight, Locale.ENGLISH);
    String bodyEs = buildBody(flight, localeSpanish);


    EmailDTO emailDTO= EmailDTO.builder()
            .to(to)
            .subject("%s / %s".formatted(subjectEn,subjectEs))
            .textBody(
                    """
                    &s
                    
                    -----------------------------------------------
                    
                    %s
                    """
                            .formatted(bodyEn,bodyEs))
            .attachment(attachmentDTOS)
            .build();
    emailService.sendEmail(emailDTO);
  }

  protected String buildBody(Flight flight, Locale locale){
    NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE MM yyyy HH:mm").withLocale(locale);

    long between = ChronoUnit.MINUTES.between(flight.getDepartureTime(), LocalDateTime.now());

    String[] args ={
            flight.getNumber(),
            flight.getDeparture().getAcronym(),
            flight.getArrival().getAcronym(),
            dateTimeFormatter.format(flight.getDepartureTime()),
            numberFormat.format(between)
    };
    return  messageSource.getMessage("flight.email.body", args,locale);

  }

  protected String buildSubject(Flight flight, Locale locale){
    String [] args = {flight.getNumber()};
    return messageSource.getMessage("flight.email.subject", args, locale);
  }


}
