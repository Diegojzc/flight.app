package com.tokioschool.flight.app.base.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
public class BookGenreRequestDTO {

    @NotBlank String genre;
}
