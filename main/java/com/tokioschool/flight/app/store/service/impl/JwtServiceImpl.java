package com.tokioschool.flight.app.store.service.impl;

import com.tokioschool.flight.app.store.config.JwtConfigurationPropeties;
import com.tokioschool.flight.app.store.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtConfigurationPropeties jwtConfigurationPropeties;

    @Override
    public Jwt generateToken(UserDetails userDetails) {

        JwsHeader jwsHeader= JwsHeader.with(MacAlgorithm.HS256).type("JWT").build();

        JwtClaimsSet jwtClaimsSet= JwtClaimsSet.builder()
                .subject(userDetails.getUsername())
                .expiresAt(Instant.now().plusMillis(jwtConfigurationPropeties.duration().toMillis()))
                .claim(
                        "authorities",
                        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwsHeader, jwtClaimsSet);
        return jwtEncoder.encode(jwtEncoderParameters);
    }
}
