package com.tokioschool.flight.app.store.controller;

import com.tokioschool.flight.app.store.dto.AuthenticationRequestDTO;
import com.tokioschool.flight.app.store.dto.AuthenticationResponseDTO;
import com.tokioschool.flight.app.store.dto.AuthenticatedMeResponseDTO;
import com.tokioschool.flight.app.store.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponseDTO> postAuthentication(
            @RequestBody AuthenticationRequestDTO authenticationRequestDTO){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequestDTO));
    }

    @GetMapping("/me")
    public AuthenticatedMeResponseDTO getAuthenticated(){
        return authenticationService.getAuthenticated();
    }
}
