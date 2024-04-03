package com.tokioschool.flight.app.dto;

import com.tokioschool.flight.app.core.validator.EnumValid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightMvcDTO {

    private Long id;
    @NotBlank
    private String number;

    @NotBlank
    private String departure;

    @NotBlank
    private String arrival;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dayTime;

    @EnumValid(target = FlightDTO.Status.class,
            message = "{validation.flight.status.invalid}",
            required = true)
    private String status;

    @Positive
    @NotNull
    @Digits(fraction = 0, integer = 3)
    private Integer capacity;

}
