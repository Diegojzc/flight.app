package com.tokioschool.flight.app.store.service.impl;

import com.tokioschool.flight.app.store.dto.AuthenticationRequestDTO;
import com.tokioschool.flight.app.store.dto.AuthenticationResponseDTO;
import com.tokioschool.flight.app.store.dto.AuthenticatedMeResponseDTO;
import com.tokioschool.flight.app.store.service.AuthenticationService;
import com.tokioschool.flight.app.store.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AutheticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  @Override
  public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationResquestDTO) {


    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    authenticationResquestDTO.getUsername(), authenticationResquestDTO.getPassword()));

    UserDetails userDetails=(UserDetails) authentication.getPrincipal();

    Jwt jwt= jwtService.generateToken(userDetails);
    return AuthenticationResponseDTO.builder()
            .accessToken(String.valueOf(jwt.getTokenValue()))
            .expiresIn(String.valueOf(ChronoUnit.SECONDS.between(Instant.now(), jwt.getExpiresAt())))
            .build();

  }

  @Override
  public AuthenticatedMeResponseDTO getAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return AuthenticatedMeResponseDTO.builder()
            .username(authentication.getName())
            .authorities(
                    authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
            )
            .build();
  }
}
