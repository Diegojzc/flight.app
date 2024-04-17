package com.tokioschool.flight.app.store.service;

import com.tokioschool.flight.app.store.dto.AuthenticationResponseDTO;
import com.tokioschool.flight.app.store.dto.AuthenticationRequestDTO;
import com.tokioschool.flight.app.store.dto.AuthenticatedMeResponseDTO;

public interface AuthenticationService {
    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationResquestDTO);
    AuthenticatedMeResponseDTO getAuthenticated();
}
