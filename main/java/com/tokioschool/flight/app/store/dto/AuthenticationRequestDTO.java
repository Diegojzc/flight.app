package com.tokioschool.flight.app.store.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized

public class AuthenticationRequestDTO {
    String username;
    String password;
}
