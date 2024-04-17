package com.tokioschool.flight.app.base.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized

public class BookSearchRequestDTO {

    String genre;
    int page;
    int pageSize;
}
