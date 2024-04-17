package com.tokioschool.flight.app.store.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class AuthenticationResponseDTO {
    String accessToken;
    String expiresIn;
}
